1. Блокнот трат
   Написать программу, для ведения личных трат.
   Цель иметь удобную инструмент для аналитики куда уходят деньги.
   Например, видеть распределение трат по категориям.


`Mcc` должен быть только в одной категории - по сути читаемое имя для кода
Категории могут быть вложены одна в другую, много-ко-многим, без циклов.


Пример:
Рестораны: 5811, 5812, 5813
Фастфуд: 5814
Супермаркеты: 5297, 5298


Еда: Рестораны, Фастфуд, Супермаркеты
Развлечения: 7911, 7922, Рестораны


**Обозначения**
`<abc>` - обязательное поле
`[xyz]` - опциональное поле
`mcc` - 4 цифры - код операции
`category` - текстовое поле


Доступные команды:
1. Добавить категорию трат
   `add category <name> <mcc> [mcc2] [mcc3] ...`
   `add-category -n <name> -m <mcc> [-m <mcc2>] [-m <mcc3>] ...`
   ответ: `created new category "name" (list of mcc)`
   ответ: `mcc already reserved for category "another name"` - если такой mcc код уже в другой категории


2. Изменить категорию трат (если с таким именем уже есть)
   `add mcc to category <name> <mcc> [mcc2] [mcc3] ...`
    `add-mcc -n <category_name> -m <mcc> [-m <mcc2>] [-m <mcc3>] ...`
   ответ: `added new mcc to category name (list of mcc)`
   ответ: `mcc already reserved for category "another name"` - если такой `mcc` код уже в другой категории


3. Изменить категорию трат
   `add group to category <name> <category to add> [category to add] ...` - добавить всю категорию в категорию
    `add-group -n <category_name> -c <category_to_add> [-c <category to add>] ...`
   ответ: `added new group to category name (list of mcc) (list of categories)`


4. Удалить
   `remove category <name>`
   `remove-category -n <name>`
   ответ: `category removed from (list of categories)`


2. Добавить трату
   `add transaction <name> <value> <month> [mcc]`
   ответ: `transaction added into category name (list of categories)`


3. Удалить трату
   `remove transaction <name> <value> <month> - первую подходящую если несколько`
   ответ: `transaction deleted`


4. Список категорий
   `show categories`
   ответ:
   `Еда
   Развлечения
   Фастфуд
   Рестораны`


5. Траты в категориях в выбранный месяц (сумма за месяц)
   `show <category name> by months`
   ответ:
   `Еда 700р 70%
   Развлечения 700р 70%
   Фастфуд 300р 30%
   Рестораны 400р 40%
   Без категории 0р 0%`


5. Траты в категории по месяцам (сумма за месяц)
   `show <category name> by months`
   `show -n <category name> -m <month>`
   ответ:
   январь 1000р
   февраль 1100р
   март 900р


Форматы запросов, ответов и операции могут отличаться при желании, но функционал должен быть и пользоваться должно быть удобно.
Данные желательно сохранять между запусками программы.