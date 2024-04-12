# 使用docker启动的容器命令
# mysql
docker run -d --name my-mysql-container \
    -e MYSQL_ROOT_PASSWORD=your-root-password \
    -e MYSQL_USER=your-username \
    -e MYSQL_PASSWORD=your-password \
    -e MYSQL_DATABASE=your-database \
    mysql:8.0.23
# redis
docker run -d --name my-redis -p 6379:6379 -e REDIS_PASSWORD=your_password redis

#rabbitmq
docker run -d --name my-rabbitmq -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=your_user -e RABBITMQ_DEFAULT_PASS=your_password rabbitmq:management

# minio
# 注意最后两个参数，如果没有设置的话，访问h5后台管理页面回失败（端口号会一直变化）
# 当上传文件时出现could not parse the specified URI 是因为端口号设置错了，一个是网页的访问端口，一个是API访问接口。
docker run -d -p 9000:9000 -p 9090:9090 --name my-minio \
  -e "MINIO_ACCESS_KEY=username" \
  -e "MINIO_SECRET_KEY=password" \
  -v /home/minio/data1:/data \
  -v /home/minio/cos.json:/root/.minio/cors-config.json \
  minio/minio server /data --console-address ":9000" --address ":9090"

# nginx
docker run --name my-nginx -p 80:80 -v /home/code/diet-recommend/front-ms/dist:/usr/share/nginx/html:ro -d nginx
