# UI+API тесты на demoqa.com

## Ссылки к проекту

- [Сборка в Jenkins](https://jenkins.autotests.cloud/job/ui-api-demoqa-develop/)
- [Allure отчет](https://jenkins.autotests.cloud/job/ui-api-demoqa-develop/allure/)

## Запуск из терминала

Удаленный запуск:
```
clean 
${TASK} 
-Dbrowser=${BROWSER}
-DbaseUrl=${BASE_URL}
-DbrowserVersion=${BROWSER_VERSION}
-DbrowserSize=${BROWSER_SIZE} 
-DwebDriverHost=${WEB_DRIVER_HOST}
-DremoteUrl=https://${AUTH}@${WEB_DRIVER_HOST}/wd/hub
-DuserName=${USER_NAME}
-Dpassword=${PASSWORD}
```
