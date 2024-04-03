# запуск в Chrome

```bash
mvn test
```

# запуск с Firefox

```bash
mvn -Dbrowser=firefox test
```

если mvn test падает с ошибкой 500, и не находит бинарник, то запускаем с параметром

```bash
mvn -Dbrowser=firefox -Dwebdriver.firefox.bin="C:\Program Files\Mozilla Firefox\firefox.exe" test
```
#   S p r i n t _ 3  
 #   s p r i n t _ 4  
 