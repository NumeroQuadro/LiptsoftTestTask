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
- **Add category to the database**: `add-category`

    add-category -n=&lt;categoryName&gt; -m=&lt;mccs&gt;[,&lt;mccs&gt;...]

   -m, --mcc=&lt;mccs&gt;[,&lt;mccs&gt;...]: MCC codes of the transaction

   -n, --name=&lt;categoryName&gt;: Name of category

- **Add group to existing category**: `add-group`

  add-group -n=&lt;categoryName&gt; -c=&lt;categories&gt;[,&lt;categories&gt;...]
  
  -c, --category=&lt;categories&gt;[,&lt;categories&gt;...]: Categories to add

  -n, --name=&lt;categoryName&gt;: Name of category

- **Add MCC to existing category**: `add-mcc`

  add-mcc -n=&lt;categoryName&gt; -m=&lt;mccs&gt;[,&lt;mccs&gt;...]

  -m, --mcc=&lt;mccs&gt;[,&lt;mccs&gt;...]: MCC codes of the transaction

  -n, --name=&lt;categoryName&gt;: Name of category


- **Add transaction to a database**: `add-transaction`

  add-transaction -a=&lt;amount&gt; -d=&lt;date&gt; [-m=&lt;mcc&gt;]

  -a, --amount=&lt;amount&gt;: Amount of transaction

  -d, --date=&lt;date&gt;: Date of transaction

  -m, --mcc=&lt;mcc&gt;: MCC of transaction

- **Remove category from the database**: `remove-category`

  -n, --name=&lt;categoryName&gt;: Name of category

- **Show category amount by period of time**: `show-all`

  -p, --period=&lt;period&gt;: Period to show stats (months)

- **Show list of categories**: `show-categories`

- **Show category amount by provided period of time**: `show`

  -n, --name=&lt;categoryName&gt;: Name of category

  -p, --period=&lt;period&gt;: Period to show stats (months)