(ns contactapp.routes.home
  (:require
   [contactapp.layout :as layout]
   [contactapp.db.core :as db]
   [clojure.java.io :as io]
   [contactapp.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]
   [struct.core :as st]))


(def message-schema
  [[:title
    st/required
    st/string]
   [:fullname
    st/required
    st/string
    {:fullname "message must contain at least 10 characters"
     :validate (fn [name] (>= (count name) 10))}]
   [:occupation
    st/required
    st/string]
   [:phonenumber
    st/required
    st/string]
   [:email
    st/required
    st/string]
   [:date_of_birth
    st/required
    st/string]
   [:sex
    st/required
    st/string]
   ])

(defn validation-message [params]
  (first (st/validate params message-schema)))

(defn home-page [request]
  (layout/render request "home.html"))

(defn save-contact! [{:keys [params]}]
  (if-let [error (validation-message params)]
    (response/bad-request {:errors error})
    (try
      (db/create-contact! params)
      (response/ok {:status :ok})
      (catch Exception e
        (response/internal-server-error {:errors {:server-error ["Failed to save contact"]}})))))


(defn get-contacts [_]
  (response/ok  (db/get-contacts)))

(defn about-page [request]
  (layout/render request "about.html"))

(defn home-routes []
  [ "" 
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/save-contact" {:post save-contact!}]
   ["/contacts" {:get get-contacts}]
   ["/about" {:get about-page}]])

