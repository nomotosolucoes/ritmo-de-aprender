(ns aprender.main
  (:require
    [aprender.eval :as eval]
    [aprender.view :as view]
    [hoplon.core :as h]
    [hoplon.dom]
    [reitit.frontend.controllers :as rfc]
    [reitit.frontend.easy :as rfe]))


(enable-console-print!)


(defn init
  []
  (rfe/start!
    view/routes
    (fn [new-match]
      (swap! view/match (fn [old-match]
                          (when new-match
                            (assoc new-match :controllers (rfc/apply-controllers (:controllers old-match) new-match))))))
    {:use-fragment true})
  (eval/code "(require '[clojure.set :as set]) (require '[clojure.string :as str])(require '[clojure.math :as math]) (math/round 1.1)")
  (h/body (view/index))
  (println "Init run"))


(defn reload
  []
  (let [step @view/step]
    (init)
    (reset! view/step step))
  (println "Reload run"))
