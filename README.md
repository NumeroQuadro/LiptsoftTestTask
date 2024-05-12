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

    _add-category -n=&lt;categoryName&gt; -m=&lt;mccs&gt;[,&lt;mccs&gt;...]_

   _-m, --mcc=&lt;mccs&gt;[,&lt;mccs&gt;...]: MCC codes of the transaction_

   _-n, --name=&lt;categoryName&gt;: Name of category_

- **Add group to existing category**: `add-group`

  _add-group -n=&lt;categoryName&gt; -c=&lt;categories&gt;[,&lt;categories&gt;...]_
  
  _-c, --category=&lt;categories&gt;[,&lt;categories&gt;...]: Categories to add_

  _-n, --name=&lt;categoryName&gt;: Name of category_

- **Add MCC to existing category**: `add-mcc`

  _add-mcc -n=&lt;categoryName&gt; -m=&lt;mccs&gt;[,&lt;mccs&gt;...]_

  _-m, --mcc=&lt;mccs&gt;[,&lt;mccs&gt;...]: MCC codes of the transaction_

  _-n, --name=&lt;categoryName&gt;: Name of category_


- **Add transaction to a database**: `add-transaction`

  _add-transaction -a=&lt;amount&gt; -d=&lt;date&gt; [-m=&lt;mcc&gt;]_

  _-a, --amount=&lt;amount&gt;: Amount of transaction_

  _-d, --date=&lt;date&gt;: Date of transaction_

  _-m, --mcc=&lt;mcc&gt;: MCC of transaction_

- **Remove category from the database**: `remove-category`

  _-n, --name=&lt;categoryName&gt;: Name of category_

- **Show category amount by period of time**: `show-all`

  _-p, --period=&lt;period&gt;: Period to show stats (months)_

- **Show list of categories**: `show-categories`

- **Show category amount by provided period of time**: `show`

  _-n, --name=&lt;categoryName&gt;: Name of category_

  _-p, --period=&lt;period&gt;: Period to show stats (months)_