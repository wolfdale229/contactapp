(ns contactapp.helper-functions
  (:require [clojure.string :refer [join]]))

(defn error-notification [errors id]
  (when-let [error (id @errors)]
    [:div.notification.is-danger
     (clojure.string/join error)]))

(defn form-group [id type value placeholder atom-name error-notification]
  [:div.field.has-text-left
   [:label.label {:for (keyword id)} id]
   error-notification
   [:div.control
    [:input.input {:type type
                   :name (keyword id)
                   :value value
                   :placeholder placeholder
                   :on-change #(swap! atom-name assoc (keyword id)  (-> % .-target .-value))
                   }]
    ]])
