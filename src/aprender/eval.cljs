(ns aprender.eval
  (:require
    [cljs.math]
    [clojure.math :as math]
    [sci.configs.applied-science.js-interop]
    [sci.configs.cljs.pprint]
    [sci.configs.cljs.test]
    [sci.configs.funcool.promesa]
    [sci.configs.hoplon.hoplon]
    [sci.configs.hoplon.javelin]
    [sci.core :as sci]
    [sci.ctx-store :as store]))


;; Necessary to avoid the error 'Attempting to call unbound fn: #'clojure.core/*print-fn*'
;; when calling `println` inside the evaluated code
(sci/alter-var-root sci/print-fn (constantly *print-fn*))
(sci/alter-var-root sci/print-err-fn (constantly *print-err-fn*))
(sci/enable-unrestricted-access!)


(def clojure-math-config
  {:namespaces {'clojure.math (sci/copy-ns cljs.math (sci/create-ns 'clojure.math))}})


(def core (sci/create-ns 'clojure.core))


(def clojure-core-config
  {:namespaces {'clojure.core {'parse-long (sci/copy-var parse-long core)
                               'parse-double (sci/copy-var parse-double core)
                               'parse-boolean (sci/copy-var parse-boolean core)
                               'parse-uuid (sci/copy-var parse-uuid core)}}})


(def all-configs
  ;; vars so that we can extract ns info

  [#'sci.configs.applied-science.js-interop/config
   #'sci.configs.cljs.pprint/config
   #'sci.configs.cljs.test/config
   #'clojure-math-config
   #'clojure-core-config
   #'sci.configs.funcool.promesa/config
   #'sci.configs.hoplon.javelin/config
   #'sci.configs.hoplon.hoplon/config])


(def sci-ctx
  (->> all-configs
    (map deref)
    (reduce
      sci/merge-opts
      (sci/init {:classes {'js js/globalThis :allow :all}}))
    ;; in .cljc, take the :cljs branch; here b/c of the bug babashka/sci#906
    (#(assoc % :features #{:cljs}))))


(store/reset-ctx! sci-ctx)


(defn code
  [str]
  (js/console.log :eval-input str)
  (let [result (try (sci/eval-string* sci-ctx str)
                 (catch :default e
                   (try (js/console.log "Evaluation failed:" (ex-message e)
                          (some-> e ex-data clj->js))
                     (catch :default _))
                   {::error (ex-message e) ::data (ex-data e)}))]
    (js/console.log :eval-result result)
    result))
