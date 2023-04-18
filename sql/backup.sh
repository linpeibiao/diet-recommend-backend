#!/bin/bash
# 数据库备份（定时任务脚本）
# 定义变量
DB_USER="root"
DB_PASSWORD="linpeibiaoxiaohu"
DB_NAME="diet_recommend"
BACKUP_DIR="/home/lpb/code/diet-recommend/databackup"
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
