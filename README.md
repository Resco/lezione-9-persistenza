
# Supermarket Checkout

We are now going to add persistency to this application.

The initial stage of the exercise now uses a database to store the prices of products, instead of a file.

## Exercise 0: make it work

Install postgresql.  On Mac, assuming that you have installed the "Homebrew" project, do

    brew install postgresql

On Ubuntu, do

    apt-get install postgresql postgresql-contrib

Then, in any case, create the databases by running

    script/create_databases.sql

The database schema is defined in the file `src/main/sql/000_create_products.sql`.  The initial state of the database is in file `src/main/sql/seed.sql`.  If you open it, you will see that the price of product "A" is set at 111.  Run the application, and verify that when we enter code "A" and press the button "Scan", the display should show "Price: 111".


## Exercise 1: persist the total

We want to make it possible to persist the state of the checkout on the database.  Even if the JVM process is shut down, we should retain the total.  For this reason we get rid of the static checkout variable in the servlet.  We will use the "repository" pattern, so that the repository will give us a new instance of the SupermarketCheckout at every request, initialized with the state that was saved in the database.

  1. Create a new table to store the total of our checkouts.  In preparation for the moment when we will support more checkouts at the same time, you will define two columns: an "id" integer column, and a "total" integer column.  You will need to add a new file `src/main/sql/002_create_checkouts.sql`.  You will also need to update the seed.sql to add a row to this new table.  Make it so that there is a checkout with id == 0.  Remember to run `script/create_databases.sh` again!

  2. Change the SupermarketCheckoutRepository class so that the metdhods findById and save do the right thing.

  3. Test your work by proving that
    - after scanning a few codes, the total displayed by `http://localhost:8080/total` is correct.
    - kill the server with control-C.  Restart it.  The total should stay the same.


## Exercise 2: more than one checkout

  We want to support more than one checkout at the time.  Define at least three checkouts in file seed.sql, with ids 0,1 and 2.  Change the controller so that when we open `http://localhost:8080/?checkout_id=1`, when we scan codes, the total that is incremented is visible at http://localhost:8080/total?checkout_id=1.

  Test that if we scan codes on `http://localhost:8080/?checkout_id=2`, we see the appropriate total at `http://localhost:8080/total?checkout_id=2`.  The total at `http://localhost:8080/total?checkout_id=1` should not change.

  Update the "total" and "reset" buttons on the UI so that they all work on the correct checkout, using the parameter checkout_id on the query string.

## What I did

Ho finito l'esercizio., modificando il CONTROLLER e l'INDEX. Ho aggiunto il metodo SAVE
Ho imparato ad avviare gli script tramite eg  ./run.sh 
