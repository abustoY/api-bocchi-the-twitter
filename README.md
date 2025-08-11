# Bocchi the Twitter API

Twitter風SNSアプリケーションのバックエンドAPI

## セットアップ

### 前提条件

- Java 17以上

### ローカル環境
```bash
./mvnw spring-boot:run
```

- アクセスURL: http://localhost:8080

使用されるプロパティファイル:
  
上記のコマンド（`./mvnw spring-boot:run`）を実行すると、`src/main/resources/application.yml` が読み込まれ、
`spring.profiles.active: local` が設定されている場合、`application-local.yml` の内容が併せて適用されます。
  
データベース接続やローカル環境特有の設定は、`application-local.yml` に記述してください。


## 技術スタック

- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- MySQL 8.0

## 認証機能

- ユーザー登録
- ログイン/ログアウト
- Cookieベースのセッション管理
- OIDC ログイン（Google）

## セキュリティ機能

- CORS設定
- パスワードのハッシュ化（BCrypt）

## APIエンドポイント

### 認証関連
- POST `/api/authentication/signup` - ユーザー登録
- POST `/api/authentication/login` - ログイン
- POST `/api/authentication/logout` - ログアウト
- GET `/api/authentication/status` - 認証状態確認
- GET `/oauth2/authorization/{provider}` - OIDC 認可開始（例: google, line）
- GET `/login/oauth2/code/{registrationId}` - OIDC コールバック（Spring Security 既定）


## テスト

### 使用技術
- JUnit 5
- AssertJ
- Database Rider（DBUnit）

### データセットの用意
テストデータは YAML で管理します。配置場所は次のとおりです。

```
src/test/resources/datasets/
  └─ tweets.yml
```

`tweets.yml` の例：

```yaml
users:
  - id: "user1"
    name: "テストユーザー"
    password: "password"

tweets:
  - id: 1
    user_id: "user1"
    text: "最初のツイート"
    created: "2024-01-01 10:00:00"
```

### テストクラスの書き方
Database Rider を使うテストでは、`@DBRider` と `@DataSet` を付与してデータを投入します。

### 補足
- YAML のキーは **テーブル名** に合わせてください（外部キーは `user_id` など実カラム名）。
- データの前後処理が必要な場合は `@DataSet(cleanBefore = true, cleanAfter = true)` を利用できます。
