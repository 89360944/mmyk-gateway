# 该镜像需要依赖的基础镜像
FROM dvmarques/openjdk-15-jdk-alpine-with-timezone
# 调整时区
RUN rm -f /etc/localtime \
&& ln -sv /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
&& echo "Asia/Shanghai" > /etc/timezone
# 将当前目录下的jar包复制到docker容器的/目录下
ADD target/mmyk-gateway-0.0.1-SNAPSHOT.jar /app/app.jar
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar","app/app.jar"]