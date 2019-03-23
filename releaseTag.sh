#!/bin/sh

function log() {
    echo -e '\n'$1'\n'
}

function readgoon() {
    echo -e '\n'$1
    read
}

NOW_DATE=`date +%Y-%m-%d`

# git tag
echo -e '\n$ git tag'
git tag

# 输入参数
echo
until [ -n ''$VERSION_NAME ]; do
	read -p "input version name: v" VERSION_NAME
done;

until [ -n ''$VERSION_CODE ]; do
	read -p "input version code: " VERSION_CODE
done;

until [ -n ''$DB_CODE ]; do
	read -p "input db      code: " DB_CODE
done;

until [ -n ''$TAG_NAME ]; do
	read -p "input tag name: v" TAG_NAME
done;

# git tag -a v1.0.0 -m "2000-01-01 publish, code = 1, db code = 1"
echo -e '\n\nversion name: v'$VERSION_NAME
echo 'version code: '$VERSION_CODE
echo 'db      code: '$DB_CODE
echo 'tag     name: '$TAG_NAME
echo -e '\n$ git tag -a v'$TAG_NAME' -m "'$NOW_DATE' publish v'$VERSION_NAME', code = '$VERSION_CODE', db code = '$DB_CODE'"'

readgoon 'verify and press any key to go on, press Ctrl + C to exit.'
git tag -a v$TAG_NAME -m $NOW_DATE' publish v'$VERSION_NAME', code = '$VERSION_CODE', db code = '$DB_CODE

# git push --tag
echo '$ git push --tag'
git push --tag

readgoon 'please press any key to go on.'

# git tag
echo -e '$ git tag'
git tag

readgoon 'please press any key to exit.'
exit
