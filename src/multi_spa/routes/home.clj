(ns multi-spa.routes.home
  (:require [multi-spa.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]))

(defn main-page []
  (layout/render "main.html"))

(defn admin-page []
  (layout/render "admin.html"))

(defroutes home-routes
  (GET "/" [] (main-page))
  (GET "/admin" [] (admin-page))
  (GET "/docs" [] (ok (-> "docs/docs.md" io/resource slurp))))

