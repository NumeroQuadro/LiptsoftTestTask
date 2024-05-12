**The Expense Tracker** 

The Expense Tracker - is a user-friendly tool designed to help 
you manage and analyze your personal expenses. 
It allows you to categorize and track where your money goes each month, 
giving you insights into your spending habits.


**Features**:
- **Categorize Expenses**:
Organize your expenditures by categories and subcategories without overlapping.
- **Track Transactions**: 
Log every expense under appropriate categories with details such as amount and date.
- **Analyze Spending**: 
View spending by category or track monthly expenses to better understand your financial habits.

**Key Operations**:
- Add category to the database:

   -m, --mcc=<mccs>[,<mccs>...]: MCC codes of the transaction

   -n, --name=<categoryName>: Name of category

- Add group to existing category

  -c, --category=<categories>[,<categories>...]: Categories to add

  -n, --name=<categoryName>: Name of category

- Add MCC to existing category

  -m, --mcc=<mccs>[,<mccs>...]: MCC codes of the transaction

  -n, --name=<categoryName>: Name of category

- add-transaction -a=<amount> -d=<date> [-m=<mcc>]

  Add transaction to a database

  -a, --amount=<amount>: Amount of transaction

  -d, --date=<date>: Date of transaction

  -m, --mcc=<mcc>: MCC of transaction

- Remove category from the database

  -n, --name=<categoryName>: Name of category

- show-all [-p=<period>]

  Show category amount by period of time

  -p, --period=<period>: Period to show stats (months, years)

- show-categories

  Show list of categories

- show -n=<categoryName> [-p=<period>]

  Show category amount by provided period of time

  -n, --name=<categoryName>: Name of category

  -p, --period=<period>: Period to show stats (months, years)