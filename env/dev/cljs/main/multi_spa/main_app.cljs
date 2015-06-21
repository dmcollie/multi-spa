(ns ^:figwheel-no-load multi-spa.main-app
  (:require [multi-spa.main.core :as main]
            [figwheel.client :as figwheel :include-macros true]))

(enable-console-print!)

(figwheel/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :jsload-callback main/mount-components)

(main/init!)
