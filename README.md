# parser

### Установка

1) Разархивируйте скачанный архив в любую папку на вашем ПК;
2) Откройте файлы через IntelliJ IDEA;

Класс DemoApplication отвечает за создание более мелких xml-файлов из файла Posts.xml

В строке 14 " File file = new File... " в скобках необходимо указать путь до файла Posts.xml

В строке 22 " FileOutputStream outputStream = new FileOutputStream " в скобках необходимо указать путь до файла в который 
будут помещены данные из файла Posts.xml


Класс readXmlToPostgres парсит XML и добавляет данные из него в базу данных

Для запуска необходимо
- установить postgresql
- в pgAdmin создать базу данных к которой будем подключаться
- создать таблицу в базе данных
CREATE TABLE USER_REQUEST(
    Id int,
    PostTypeId INTEGER,
    AcceptedAnswerId INTEGER,
    CreationDate timestamp,
    Score INTEGER,
    ViewCount INTEGER,
    Body varchar,
    OwnerUserId INTEGER,
    LastEditorUserId INTEGER,
    LastEditorDisplayName varchar,
    LastEditDate timestamp,
    LastActivityDate timestamp,
    Title varchar,
    Tags varchar,
    AnswerCount INTEGER,
    CommentCount INTEGER,
    FavoriteCount INTEGER,
    CommunityOwnedDate timestamp,
    ContentLicense varchar,
    ParentId INTEGER,
    OwnerDisplayName varchar,
    ClosedDate timestamp
);

В строке 28  "XMLEventReader eventReader = factory.createXMLEventReader ..." необходимо указать путь до xml файла

В createConnection() необходимо указать данные для подключения к базе данных
