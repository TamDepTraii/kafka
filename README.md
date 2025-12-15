# Spring Boot Kafka Message Broker Demo

Dự án này là một ví dụ minh họa về cách xây dựng hệ thống Message Broker sử dụng **Apache Kafka** và **Spring Boot**. Hệ thống bao gồm một cụm Kafka (Kafka Cluster) chạy trên Docker, một ứng dụng Producer gửi tin nhắn định kỳ, và một ứng dụng Consumer nhận và xử lý tin nhắn.

## 🏗 Kiến trúc hệ thống

Hệ thống bao gồm các thành phần chính sau:

1.  **Kafka Cluster**:
    *   Chạy trên Docker Compose.
    *   Gồm 1 node **Zookeeper**.
    *   Gồm 3 node **Kafka Broker** (`kafka1`, `kafka2`, `kafka3`) để đảm bảo tính sẵn sàng cao (High Availability).
    *   Cấu hình Replication Factor = 3 (dữ liệu được sao chép ra cả 3 node).

2.  **Producer Service** (`message-broker-kafka-producer`):
    *   Ứng dụng Spring Boot.
    *   Tự động tạo và gửi object `User` (JSON) tới topic `user-topic` mỗi 3 giây.
    *   Sử dụng `KafkaTemplate` và `JsonSerializer`.

3.  **Consumer Service** (`message-broker-kafka-consumer`):
    *   Ứng dụng Spring Boot.
    *   Lắng nghe topic `user-topic` thuộc group `user-group-1`.
    *   Tự động deserialize JSON thành object `User`.
    *   In thông tin user nhận được ra console.

## 📋 Yêu cầu cài đặt

*   **Java 17** trở lên.
*   **Docker** và **Docker Compose** (để chạy Kafka Cluster).
*   **Maven** (đã có sẵn Maven Wrapper `mvnw` trong dự án).

## 🚀 Hướng dẫn chạy

### Bước 1: Khởi động Kafka Cluster

Mở terminal tại thư mục gốc của dự án (nơi chứa file `docker-compose.yml`) và chạy lệnh:

```bash
docker-compose up -d
```

Đợi một lát để các container `zookeeper`, `kafka1`, `kafka2`, `kafka3` khởi động hoàn toàn.

### Bước 2: Chạy Consumer

Mở một terminal mới, di chuyển vào thư mục consumer và chạy ứng dụng:

```bash
cd message-broker-kafka-consumer
./mvnw spring-boot:run
```
*(Trên Windows Command Prompt: `mvnw.cmd spring-boot:run`)*

Consumer sẽ khởi động và chờ tin nhắn từ topic `user-topic`.

### Bước 3: Chạy Producer

Mở một terminal khác, di chuyển vào thư mục producer và chạy ứng dụng:

```bash
cd message-broker-kafka-producer
./mvnw spring-boot:run
```
*(Trên Windows Command Prompt: `mvnw.cmd spring-boot:run`)*

Producer sẽ bắt đầu gửi tin nhắn `User` mỗi 3 giây.

### Bước 4: Kiểm tra kết quả

Quan sát terminal của **Consumer**, bạn sẽ thấy log hiển thị tin nhắn nhận được:

```text
<- Received user: User 1 | Email: user1@example.com
<- Received user: User 2 | Email: user2@example.com
...
```

Quan sát terminal của **Producer**, bạn sẽ thấy log hiển thị tin nhắn đã gửi:

```text
-> Sent user: User 1
-> Sent user: User 2
...
```

## 📂 Cấu trúc dự án

```
kafka/
├── docker-compose.yml              # Cấu hình Docker cho Kafka Cluster
├── message-broker-kafka-consumer/  # Source code ứng dụng Consumer
│   ├── src/main/java/.../consumer/ConsumerService.java
│   └── src/main/resources/application.yml
└── message-broker-kafka-producer/  # Source code ứng dụng Producer
    ├── src/main/java/.../producer/ProducerService.java
    └── src/main/resources/application.yml
```

## ⚙️ Cấu hình chính

*   **Topic**: `user-topic`
*   **Bootstrap Servers**: `localhost:19091`, `localhost:19092`, `localhost:19093`
*   **Consumer Group ID**: `user-group-1`

## 🛑 Dừng hệ thống

Để dừng Kafka Cluster và xóa các container:

```bash
docker-compose down
```
