(ns contactapp.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[contactapp started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[contactapp has shut down successfully]=-"))
   :middleware identity})
