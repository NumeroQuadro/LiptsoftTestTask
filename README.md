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
- **Add category to the database:**

   -m, --mcc=&lt;mccs&gt;[,&lt;mccs&gt;...]: MCC codes of the transaction

   -n, --name=&lt;categoryName&gt;: Name of category

- **Add group to existing category**

  -c, --category=&lt;categories&gt;[,&lt;categories&gt;...]: Categories to add

  -n, --name=&lt;categoryName&gt;: Name of category

- **Add MCC to existing category**

  -m, --mcc=&lt;mccs&gt;[,&lt;mccs&gt;...]: MCC codes of the transaction

  -n, --name=&lt;categoryName&gt;: Name of category

  add-transaction -a=&lt;amount&gt; -d=&lt;date&gt; [-m=&lt;mcc&gt;]

- **Add transaction to a database**

  -a, --amount=&lt;amount&gt;: Amount of transaction

  -d, --date=&lt;date&gt;: Date of transaction

  -m, --mcc=&lt;mcc&gt;: MCC of transaction

- **Remove category from the database**

  -n, --name=&lt;categoryName&gt;: Name of category

  show-all [-p=&lt;period&gt;]

- **Show category amount by period of time**

  -p, --period=&lt;period&gt;: Period to show stats (months, years)

  show-categories

- **Show list of categories**

  show -n=&lt;categoryName&gt; [-p=&lt;period&gt;]

- **Show category amount by provided period of time**

  -n, --name=&lt;categoryName&gt;: Name of category

  -p, --period=&lt;period&gt;: Period to show stats (months)