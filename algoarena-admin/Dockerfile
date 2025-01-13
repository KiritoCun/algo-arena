# Sử dụng image GraalVM làm base
FROM findepi/graalvm:java17-native

MAINTAINER HoaLD

# Tạo các thư mục cần thiết
RUN mkdir -p /algorith-arena/server/logs \
    /algorith-arena/server/temp \
    /algorith-arena/skywalking/agent

# Chuyển thư mục làm việc
WORKDIR /algorith-arena/server

# Sao chép toàn bộ mã nguồn vào container
COPY . .

# Cấp quyền thực thi cho tệp mvnw
RUN chmod +x mvnw

# Build ứng dụng
RUN ./mvnw clean package -DskipTests

# Chỉ định cổng cho ứng dụng
ENV SERVER_PORT=8080

# Expose cổng để container có thể nhận traffic
EXPOSE ${SERVER_PORT}

# Thiết lập điểm vào cho container
ENTRYPOINT ["java", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-Dserver.port=${SERVER_PORT}", \
            "-jar", "target/algoarena-admin.jar"]
