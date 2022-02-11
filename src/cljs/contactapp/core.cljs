(ns contactapp.core
  (:require [reagent.core :as r]
            [reagent.dom :as dom]
            [ajax.core :refer [GET POST]]
            [contactapp.helper-functions :refer [form-group error-notification]]))



(defn get-contacts [contact]
  (GET "/contacts" {:format :json
                    :handler #(reset! contact %)
                    }))

(defn save-contacts [form errors]
  (POST "/save-contact" {:headers {"Accept" "application/transit+json"
                                   "x-csrf-token" (.-value (.getElementById js/document "token"))}
                         :format :json
                         :params @form
                         :handler (fn [r]
                                    (.log js/console (str "response: " r))
                                    (reset! errors nil))
                         :error-handler (fn [e]
                                          (.error js/console (str "error: " e))
                                          (reset! errors (-> e :response :errors)))}))

(defn contact-form []
  (let [form (r/atom {})
        errors (r/atom nil)]
    (fn []
      [:div.hero
       [:div.hero-body
        [:div.container
         [:div.columns
          [:div.column
           [:form.box
            [:div.field
             [error-notification errors :server-error]]

            [form-group "title" :text (:title @form) "Mr/Mrs/Miss" form
             (error-notification errors :title)]

            [form-group "fullname" :text (:fullname @form) "Alex Jefferson" form
             (error-notification errors :fullname)]

            [form-group "occupation" :text (:occupation @form) "Historian" form
             (error-notification errors :occupation)]

            [form-group "phonenumber" :text (:phonenumber @form) "000-0000-000" form
             (error-notification errors :phonenumber)]

            [form-group "email" :email (:email @form) "AlexJef@company.com" form
             (error-notification errors :email)]

            [form-group "date_of_birth" :text (:date_of_birth @form) "January 1 1990" form
             (error-notification errors :date_of_birth)]

            [form-group "sex" :text (:sex @form) "Female, Male, Other" form
             (error-notification errors :sex)]

            [:div.field
             [:input.button.is-success {:type :submit
                                        :value "Save"
                                        :on-click #(save-contacts form errors)}]]]]]]]])))
(defn contact-component []
  (let [contacts (r/atom nil)]
    (get-contacts contacts)
    (fn []
      [:nav.menu.hero
       [:h1.menu-label.title
        "Contacts"]
       [:form.box
        [:ul.menu-list
         (for [{:keys [id title fullname occupation email phonenumber date_of_birth sex]} @contacts]
           [:li {:key id}
            [:div.navbar-item.has-dropdown.is-hoverable
             [:div.navbar-link
              (clojure.string/capitalize title) " " (clojure.string/capitalize fullname)]
             [:hr]
             [:div.navbar-dropdown
              [:div.navbar-item
               (str "Occupation " occupation)]
              [:br]
              [:div.navbar-item email]
              [:br]
              [:div.navbar-item phonenumber]
              [:br]
              [:div.navbar-item date_of_birth]
              [:br]
              [:div.navbar-item sex]
              ]]])]]])))
  

(defn home []
      [:div.main
       [:div.columns
        [:div.column
         [contact-component] ;navbar-menu for viewing saved contacts.
         ]
        [:div.column.is-two-thirds
         ;; form for creating a contact.
         [:p.title "Contact Form"]
         [contact-form]]
        ]])

(dom/render
 [home]
 (.getElementById js/document "content"))
