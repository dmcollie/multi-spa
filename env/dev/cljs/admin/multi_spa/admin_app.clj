(ns ^:figwheel-no-load multi-spa.admin-app
  (:require [multi-spa.admin.core :as admin]
            [figwheel.client :as figwheel :include-macros true]))

(enable-console-print!)

(figwheel/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :jsload-callback admin/mount-components)

(admin/init!)
