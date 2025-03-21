Проектът за билетна система започна на 21.02.2024 с хранилище с име my-project.tu.

През Седмица 1 бяха добавени класовете Hall и Main. Класът Hall описва зала с номер, брой редове и брой места на ред. Класът Main съдържа началната реализация на програмата за управление на зали.

През Седмица 2 бе добавен клас Event, който разширява функционалността на програмата с управление на представления. Класът Event съдържа информация за името на представлението, датата му и залата, в която ще се проведе. Също така бе разширен Main класът, като бяха добавени 5 зали с различен капацитет и ново меню за управление на представления: добавяне на ново представление, показване на всички представления и изход от програмата.

През Седмица 3 бе добавен клас Ticket и нова функционалност за закупуване на билети. Класът Ticket съдържа уникален код на билета, ред и място в залата, както и представление, за което е билета. Добавени са гетъри, конструктор и метод toString() за по-лесно извеждане на информация. В Main класа е добавен списък с билети и разширено меню с нови опции. Потребителят може да закупи билет, като въвежда дата, име на представлението, ред и място, след което се генерира уникален код за билета. Освен това се извършва проверка за валидност на мястото и дали е свободно. Добавена е опция за показване на всички закупени билети и възможност за изход от програмата.

През Седмица 4 е добавен клас Reservation и нови функционалности в клас Main за управление на резервации. Класът Reservation съдържа информация за ред, място, представление и бележка (например име на резервиралия). Добавени са гетъри за тези полета, както и метод toString(), който улеснява визуализирането на резервациите.
В клас Main беше добавена нова логика, която позволява резервиране на места с проверка за наличност, отмяна на резервации и показване на свободни места в залите. В допълнение, беше подобрена обработката на грешки и валидацията на входните данни, за да се осигури по-добро потребителско изживяване.
