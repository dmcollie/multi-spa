(defproject multi-spa "0.1.0-SNAPSHOT"

            :description "FIXME: write description"
            :url "http://example.com/FIXME"

            :dependencies [[org.clojure/clojure "1.7.0-RC1"]
                           [selmer "0.8.2"]
                           [com.taoensso/timbre "3.4.0"]
                           [com.taoensso/tower "3.0.2"]
                           [markdown-clj "0.9.66"]
                           [environ "1.0.0"]
                           [compojure "1.3.4"]
                           [ring/ring-defaults "0.1.5"]
                           [ring/ring-session-timeout "0.1.0"]
                           [metosin/ring-middleware-format "0.6.0"]
                           [metosin/ring-http-response "0.6.2"]
                           [bouncer "0.3.3"]
                           [prone "0.8.2"]
                           [org.clojure/tools.nrepl "0.2.10"]
                           [ring-server "0.4.0"]
                           [org.clojure/clojurescript "0.0-3308" :scope "provided"]
                           [org.clojure/tools.reader "0.9.2"]
                           [reagent "0.5.0"]
                           [cljsjs/react "0.13.3-0"]
                           [reagent-forms "0.5.1"]
                           [reagent-utils "0.1.4"]
                           [secretary "1.2.3"]
                           [aprint "0.1.3"]
                           [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                           [cljs-ajax "0.3.13"]]

            :min-lein-version "2.0.0"
            :uberjar-name "multi-spa.jar"
            :jvm-opts ["-server"]

            ;;enable to start the nREPL server when the application launches
            ;:env {:repl-port 7001}

            :main multi-spa.core

            :plugins [[lein-ring "0.9.1"]
                      [lein-environ "1.0.0"]
                      [lein-ancient "0.6.5"]
                      [lein-cljsbuild "1.0.6"]]



            :ring {:handler      multi-spa.handler/app
                   :init         multi-spa.handler/init
                   :destroy      multi-spa.handler/destroy
                   :uberwar-name "multi-spa.war"}


            :clean-targets ^{:protect false} ["resources/public/js"]

            :hooks       [leiningen.cljsbuild]
            :cljsbuild
            {:builds
             {:main-app
              {:source-paths ["src-main-cljs"]
               :compiler
                             {:output-dir    "resources/public/js/main-app/out"
                              :externs       ["react/externs/react.js"]
                              :optimizations :none
                              :output-to     "resources/public/js/main-app/main-app.js"
                              :pretty-print  true}}
              :admin-app
              {:source-paths ["src-admin-cljs"]
               :compiler
                             {:output-dir    "resources/public/js/admin-app/out"
                              :externs       ["react/externs/react.js"]
                              :optimizations :none
                              :output-to     "resources/public/js/admin-app/admin-app.js"
                              :pretty-print  true}}}}


            :profiles
            {:uberjar {:omit-source true
                       :env         {:production true}
                       :cljsbuild
                                    {:jar true
                                     :builds
                                          {:app
                                           {:source-paths ["env/prod/cljs"]
                                            :compiler     {:optimizations :advanced :pretty-print false}}}}

                       :aot         :all}
             :dev     {:dependencies [[ring-mock "0.1.5"]
                                      [ring/ring-devel "1.3.2"]
                                      [pjstadig/humane-test-output "0.7.0"]
                                      [lein-figwheel "0.3.3"]
                                      [org.clojure/tools.nrepl "0.2.10"]]
                       :plugins      [[lein-figwheel "0.3.3"]]
                       :cljsbuild
                                     {:builds
                                      {:main-app
                                       {:source-paths ["env/dev/cljs/main" "src-main-cljs"] :compiler {:source-map true}}
                                       :admin-app
                                       {:source-paths ["env/dev/cljs/admin" "src-admin-cljs"] :compiler {:source-map true}}}}

                       :figwheel
                                     {:http-server-root "public"
                                      :server-port      3449
                                      :nrepl-port       7002
                                      :css-dirs         ["resources/public/css"]
                                      :ring-handler     multi-spa.handler/app}

                       :repl-options {:init-ns multi-spa.core}
                       :injections   [(require 'pjstadig.humane-test-output)
                                      (pjstadig.humane-test-output/activate!)]
                       :env          {:dev true}}})
