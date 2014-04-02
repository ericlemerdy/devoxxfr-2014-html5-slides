#!/bin/sh
PACKAGE_NAME=$1

DOC_LINK=/usr/share/nginx/html
CURR_ENV=$(basename $(readlink ${DOC_LINK}))
if [ "_${CURR_ENV}" = "_blue" ]; then
  TARGET_ENV=green
else
  TARGET_ENV=blue
fi

TARGET_DIR=/var/www/${TARGET_ENV}
if [ -d ${TARGET_DIR} ]; then
  rm -rf ${TARGET_DIR}
  mkdir ${TARGET_DIR}
fi

tar xzf /var/www/${PACKAGE_NAME} -C ${TARGET_DIR}
rm -f /var/www/${PACKAGE_NAME}

ln -sf ${TARGET_DIR} ${DOC_LINK}
