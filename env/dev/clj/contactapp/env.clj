(ns contactapp.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [contactapp.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[contactapp started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[contactapp has shut down successfully]=-"))
   :middleware wrap-dev})
