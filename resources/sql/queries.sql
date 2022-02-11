-- :name create-contact! :! :n
-- :doc creates a new contact
INSERT INTO contactapp
            (title, fullname, occupation, phonenumber, email, date_of_birth, sex )
VALUES (:title, :fullname, :occupation, :phonenumber, :email, :date_of_birth, :sex)

-- :name get-contacts :? :*
-- :doc retrieves all contacts
SELECT * FROM contactapp
