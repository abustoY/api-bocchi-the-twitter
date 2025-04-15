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

## セキュリティ機能

- CORS設定
- パスワードのハッシュ化（BCrypt）

## APIエンドポイント

### 認証関連
- POST `/api/authentication/signup` - ユーザー登録
- POST `/api/authentication/login` - ログイン
- POST `/api/authentication/logout` - ログアウト
- GET `/api/authentication/status` - 認証状態確認

### ユーザー関連
- GET `/api/users/{id}` - ユーザー情報取得
- PUT `/api/users/{id}` - ユーザー情報更新


