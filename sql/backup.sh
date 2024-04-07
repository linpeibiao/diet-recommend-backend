#!/bin/bash
# 数据库备份（定时任务脚本）
# 定义变量
DB_USER="root"
DB_PASSWORD="123456"
DB_NAME="diet_recommend"
BACKUP_DIR="/home/code/diet-recommend/databackup"
DATE=$(date +%Y-%m-%d_%H-%M-%S)
DAYS_TO_KEEP=10

# 创建备份目录
mkdir -p $BACKUP_DIR

# 备份数据库
docker exec mysql mysqldump -u $DB_USER -p$DB_PASSWORD $DB_NAME > $BACKUP_DIR/$DB_NAME-$DATE.sql

# 将备份文件复制到宿主机上
docker cp mysql:$BACKUP_DIR/$DB_NAME-$DATE.sql $BACKUP_DIR

# 压缩备份文件
gzip $BACKUP_DIR/$DB_NAME-$DATE.sql

# 删除过期备份文件
find $BACKUP_DIR -type f -mtime +$DAYS_TO_KEEP -name '*.gz' -delete

## 创建备份目录
#mkdir -p $BACKUP_DIR
#
## 备份数据库并直接写入宿主机的备份目录
#docker exec e57bcf0b4a33 mysqldump -u $DB_USER -p$DB_PASSWORD $DB_NAME > /1.sql
#
## 将备份文件复制到宿主机上
#docker cp e57bcf0b4a33:/1.sql $BACKUP_DIR
#
## 重命名
#mv $BACKUP_DIR/1.sql $BACKUP_DIR/$DB_NAME-$DATE.sql
#
## 压缩备份文件
#gzip $BACKUP_DIR/$DB_NAME-$DATE.sql
#
## 删除过期备份文件
#find $BACKUP_DIR -type f -mtime +$DAYS_TO_KEEP -name '*.gz' -delete

