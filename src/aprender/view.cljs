(ns aprender.view
  (:require
    [aprender.clojure :as clojure]
    [aprender.eval :as eval]
    [clojure.string :as str]
    [clojure.walk :as walk]
    [hoplon.core :as h :refer [div]]
    [hoplon.svg :as svg]
    [javelin.core :as j]
    [nextjournal.markdown :as markdown]
    [reitit.frontend :as rf]))


(defn botao-link
  ([link texto]
    (botao-link link texto nil))
  ([link texto css]
    (div :css css
      (h/a :css {:color "white"
                 :text-decoration "none"
                 :line-height "1.25rem"
                 :padding-top "0.25rem"
                 :padding-bottom "0.25rem"
                 :padding-left "0.75rem"
                 :padding-right "0.75rem"
                 :background-color "rgb(79, 70, 229)"
                 :white-space "nowrap"
                 :border-radius ".375rem"
                 :pointer-events "auto"}

        :href (str "#/" (name link))
        texto))))


(defn copy-to-clipboard
  [text on-success on-error]
  (.then (js/navigator.clipboard.writeText text)
    on-success
    on-error))


(defn scroll-into-view
  [el]
  (let [rect (.getBoundingClientRect el)
        bottom-relative-to-viewport (.-bottom rect)
        new-top (- (+ js/window.scrollY bottom-relative-to-viewport) js/window.innerHeight)]
    (js/window.scrollTo #js {:top new-top :behavior "smooth"})))


(defonce show-modal? (j/cell false))


(defmethod h/do! :show-modal?
  [elem _ v]
  (if v
    (when-not (.-open elem)
      (.showModal elem))
    (when (.-open elem)
      (.close elem))))


(h/defelem p
  [_attr kids]
  (apply h/p :css {:margin "12px 0"}
    kids))


(defn code
  [& s]
  (h/code :css {:background-color "rgb(238, 240, 240)"} :dangerous-html (js/Prism.highlight (str/join s) js/Prism.languages.clojure "clojure")))


(defonce step (j/cell 0))
(defonce route (j/cell :intro))


(defn- proximo-conteudo
  []
  (swap! step inc))


(defn codigo
  [& children]
  (div :css {:padding-left "12px"
             :padding-rigth "12px"
             :padding-top "12px"
             :padding-bottom "12px"
             :font-family "monospace"
             :border "1px solid"
             :background-color "rgb(245, 242, 240)"}
    (h/pre :css {:margin-top 0
                 :margin-bottom 0}
      children)))


(defn- resultado
  []
  (div :css {:padding-bottom "4px" :padding-top "12px"} "RESULTADO:"))


(defn- checkmark
  []
  (h/span :css {:vertical-align "middle" :display "inline-block" :margin-left "4px"}
    (svg/svg :width "16px" :height "16px" :viewBox "0 0 24 24" :fill "none" :xmlns "http://www.w3.org/2000/svg" :color "#18b116" :stroke-width "1.5"
      (svg/path
        :fill-rule "evenodd" :clip-rule "evenodd" :d "M12 1.25C6.06294 1.25 1.25 6.06294 1.25 12C1.25 17.9371 6.06294 22.75 12 22.75C17.9371 22.75 22.75 17.9371 22.75 12C22.75 6.06294 17.9371 1.25 12 1.25ZM7.53044 11.9697C7.23755 11.6768 6.76268 11.6768 6.46978 11.9697C6.17689 12.2626 6.17689 12.7374 6.46978 13.0303L9.46978 16.0303C9.76268 16.3232 10.2376 16.3232 10.5304 16.0303L17.5304 9.03033C17.8233 8.73744 17.8233 8.26256 17.5304 7.96967C17.2375 7.67678 16.7627 7.67678 16.4698 7.96967L10.0001 14.4393L7.53044 11.9697Z" :fill "#18b116"))))


(defn- resposta-usuario
  [{:keys [expected-code before reset]}]
  (let [error? (j/cell false)
        success? (j/cell false)
        input-value (j/cell "")
        expected (str/join "\n" (concat before expected-code))]
    (h/if-tpl success?
      (h/span
        (h/span :css {:vertical-align "middle" :display "inline-block"}
          (h/code :dangerous-html (j/cell= (js/Prism.highlight input-value js/Prism.languages.clojure "clojure"))))
        (checkmark))
      (div
        (let [i (h/input :class "resposta"
                  :autocomplete "off"
                  :autocorrect "off"
                  :spellcheck "false"
                  :css (j/cell= {:border-color (if error? "red" "black")
                                 :line-height "140%"
                                 :outline "none"})
                  :keydown #(reset! error? false)
                  :keyup #(if (= "Enter" (.-key %))
                            (when-not (str/blank? @%)
                              (some-> reset eval/code)
                              (if (= (eval/code expected) (eval/code (str/join "\n" (concat before [@%]))))
                                (do (proximo-conteudo)
                                  (reset! success? true))
                                (reset! error? true)))
                            (reset! input-value @%)))]
          (h/when-dom i #(.focus i))
          i)
        (h/button :click #(do
                            (some-> reset eval/code)
                            (if (= (eval/code expected) (eval/code (str/join "\n" (concat before [@input-value]))))
                              (do (proximo-conteudo)
                                (reset! success? true))
                              (reset! error? true)))
          "Executar")))))


(defn debug
  []
  (div
    "Rota: " (j/cell= (name route))
    (div "Step: " step)
    (div
      (h/input :change #(reset! step (parse-long @%)) :value step)
      (h/button :click #(swap! step dec) "-")
      (h/button :click #(swap! step inc) "+")
      (h/button :click #(reset! show-modal? true) "REPL")
      (h/button :click (fn [_ev]
                         (copy-to-clipboard "meu texto" #(js/console.log :copiou) #(js/console.error :naocopiou)))
        "Copiar"))
    (div :css {:padding-top "0.25rem"
               :display "flex"
               :justify-content "flex-start"
               :align-content "space-between"
               :flex-wrap "wrap"}
      (botao-link "" "Índice" {:margin-right "4px" :margin-top "8px"})
      (botao-link :intro "Intro" {:margin-right "4px" :padding-top "8px"})
      (botao-link :tipos-de-dados "Tipos" {:margin-right "4px" :margin-top "8px"})
      (botao-link :colecoes "Coleções" {:margin-right "4px" :margin-top "8px"})
      (botao-link :funcoes-1 (:title clojure/funcoes-1) {:margin-right "4px" :margin-top "8px"})
      (botao-link :funcoes-2 (:title clojure/funcoes-2) {:margin-right "4px" :margin-top "8px"})
      (botao-link :booleanos "Booleanos" {:margin-right "4px" :margin-top "8px"})
      (botao-link :strings "Strings" {:margin-right "4px" :margin-top "8px"})
      (botao-link :substrings (:title clojure/substrings) {:margin-right "4px" :margin-top "8px"})
      (botao-link :numeros-1 (:title clojure/numeros-1) {:margin-right "4px" :margin-top "8px"})
      (botao-link :numeros-2 (:title clojure/numeros-2) {:margin-right "4px" :margin-top "8px"})
      (botao-link :numeros-3 (:title clojure/numeros-3) {:margin-right "4px" :margin-top "8px"})
      (botao-link :numeros-4 (:title clojure/numeros-4) {:margin-right "4px" :margin-top "8px"})
      (botao-link :definicoes "Definições" {:margin-right "4px"  :margin-top "8px"})
      (botao-link :condicionais "Condicionais" {:margin-right "4px" :margin-top "8px"})
      (botao-link :sigla "Sigla" {:margin-right "4px" :margin-top "8px"}))))


(defn md-str->elem
  [md-str]
  (walk/postwalk (fn [arg]
                   (if (map? arg)
                     (let [{:keys [type text content heading-level]} arg]
                       (case type
                         :text text
                         :doc (apply div content)
                         :paragraph (apply p content)
                         :monospace (apply code content)
                         :plain content
                         :list-item (apply h/li content)
                         :bullet-list (apply h/ul content)
                         :heading (apply (case heading-level
                                           1 h/h1
                                           2 h/h2
                                           3 h/h3
                                           4 h/h4
                                           5 h/h5)
                                    content)
                         :toc nil
                         nil nil
                         (js/console.error arg)))
                     arg))
    (markdown/parse md-str)))


(defn- mostrar-exemplo
  ([passo-inicial exemplo]
    (h/when-tpl (j/cell= (>= step passo-inicial))
      (let [d (div :css {:padding-bottom "12px"}
                (codigo
                  (div :dangerous-html (js/Prism.highlight exemplo js/Prism.languages.clojure "clojure"))
                  (resultado)
                  (h/when-tpl (j/cell= (= step passo-inicial))
                    (let [b (h/button :click #(proximo-conteudo)
                              "Executar código")]
                      (h/when-dom b #(.focus b))
                      b))
                  (h/when-tpl (j/cell= (>= step (inc passo-inicial)))
                    (h/span
                      (h/span :css {:vertical-align "middle" :display "inline-block"}
                        (code (pr-str (eval/code exemplo))))
                      (checkmark)))))]
        (h/when-dom d #(scroll-into-view d))
        d)))
  ([passo-inicial texto-ou-elem exemplo]
    (h/when-tpl (j/cell= (>= step passo-inicial))
      (let [d (div :css {:padding-bottom "12px"}
                (cond
                  (string? texto-ou-elem) (p texto-ou-elem)
                  (vector? texto-ou-elem) (md-str->elem (str/join "\n\n" texto-ou-elem))
                  :else texto-ou-elem)
                (codigo
                  (div :dangerous-html (js/Prism.highlight exemplo js/Prism.languages.clojure "clojure"))
                  (resultado)
                  (h/when-tpl (j/cell= (= step passo-inicial))
                    (let [b (h/button :click #(proximo-conteudo)
                              "Executar código")]
                      (h/when-dom b #(.focus b))
                      b))
                  (h/when-tpl (j/cell= (>= step (inc passo-inicial)))
                    (h/span
                      (h/span :css {:vertical-align "middle" :display "inline-block"}
                        (code (pr-str (eval/code exemplo))))
                      (checkmark)))))]
        (h/when-dom d #(scroll-into-view d))
        d))))


(defn mostrar-texto
  [texto-ou-elem]
  (cond
    (nil? texto-ou-elem) ""
    (string? texto-ou-elem) (p texto-ou-elem)
    (vector? texto-ou-elem) (md-str->elem (str/join "\n\n" texto-ou-elem))
    :else texto-ou-elem))


(defn- exemplo-interativo
  ([{:keys [passo-inicial code-before code code-reset texto-ou-elem]}]
    (h/when-tpl (j/cell= (>= step passo-inicial))
      (let [d (div :css {:padding-bottom "12px"}
                (mostrar-texto texto-ou-elem)
                (let [code-str (str/join "\n" (concat code-before code))]
                  (codigo
                    (div :class "language-clojure" :dangerous-html (js/Prism.highlight code-str js/Prism.languages.clojure "clojure"))
                    (resultado)
                    (resposta-usuario {:expected-code code
                                       :reset code-reset
                                       :before code-before}))))]
        (h/when-dom d #(scroll-into-view d))
        d)))
  ([passo-inicial exemplo]
    (exemplo-interativo {:passo-inicial passo-inicial
                         :codigo exemplo}))
  ([passo-inicial texto-ou-elem exemplo]
    (exemplo-interativo {:passo-inicial passo-inicial
                         :codigo exemplo
                         :texto-ou-elem texto-ou-elem})))


(defn- pre-process-description
  [description]
  (if (string? description)
    [description]
    description))


(defn- pre-process-code
  [code]
  (if (vector? code)
    (str/join "\n" code)
    code))


(defn- pre-process-code-v
  [code]
  (if (vector? code)
    code
    [code]))


(defn- resposta-usuario-area
  [{:keys [error? input-value user-result submit]}]
  (div
    (let [i (h/textarea :class "resposta"
              :autocomplete "off"
              :autocorrect "off"
              :spellcheck "false"
              :disabled (j/cell= (when (:correct? user-result) "true"))
              :readonly (j/cell= (when (:correct? user-result) "true"))
              :css (j/cell= {:border-color (if error? "red" "black")
                             :line-height "140%"
                             :width "98%"
                             :outline "none"})
              :rows (j/cell= (inc (count (str/split-lines input-value))))
              :value input-value
              :keydown #(do
                          (reset! error? false)
                          (when (and (.-ctrlKey %)
                                  (= "Enter" (.-key %)))
                            (submit)))
              :keyup #(reset! input-value @%))]
      (h/when-dom i #(.focus i)) i)))


(defn- resposta-usuario-input
  [{:keys [error? input-value user-result submit]}]
  (div
    (let [i (h/input :class "resposta"
              :autocomplete "off"
              :autocorrect "off"
              :spellcheck "false"
              :disabled (j/cell= (when (:correct? user-result) "true"))
              :readonly (j/cell= (when (:correct? user-result) "true"))
              :css (j/cell= {:border-color (if error? "red" "black")
                             :line-height "140%"
                             :width "98%"
                             :outline "none"})
              :value input-value
              :keydown #(do
                          (reset! error? false)
                          (when (= "Enter" (.-key %))
                            (submit)))
              :keyup #(reset! input-value @%))]
      (h/when-dom i #(.focus i)) i)))


(defn- objetivo
  [o]
  (div :css {:padding-bottom "4px" :padding-top "12px"} "Objetivo:  " (code (str/join "\n" o))))


(defn- seu-resultado
  [user-result]
  (div :css {:padding-bottom "4px" :padding-top "12px"}
    "Resultado: " (h/span
                    (h/span :css {:vertical-align "middle" :display "inline-block"}
                      (h/code :dangerous-html (h/code :dangerous-html (j/cell= (js/Prism.highlight (cond
                                                                                                     (= ::empty user-result) ""
                                                                                                     (:correct? user-result) (pr-str (:result user-result))
                                                                                                     (-> user-result :result ::eval/error) (str (-> user-result :result ::eval/error) "\n"
                                                                                                                                             (-> user-result :result ::eval/data (select-keys [:line :column]) pr-str))
                                                                                                     :else (pr-str (:result user-result)))
                                                                                 js/Prism.languages.clojure "clojure")))))
                    (h/when-tpl (j/cell= (:correct? user-result))
                      (checkmark)))))


(defn- exemplo-area-interativa
  [{:keys [subtype passo-inicial code-before code-template code-answer code-reset code-after texto-ou-elem expected-result]}]
  (h/when-tpl (j/cell= (>= step passo-inicial))
    (let [d (let [user-result (j/cell ::empty)
                  error? (j/cell false)
                  input-value (j/cell (str/join "\n" code-template))
                  submit #(let [expected (eval/code (str/join "\n" (concat code-before code-answer code-after)))
                                _ (eval/code (str/join "\n" code-reset))
                                actual  (eval/code (str/join "\n" (concat code-before [@input-value] code-after)))]
                            (js/console.log {:e expected
                                             :a actual})
                            (if (= expected actual)
                              (do
                                (proximo-conteudo)
                                (reset! user-result {:result actual
                                                     :correct? true}))
                              (do
                                (reset! user-result {:result actual
                                                     :correct? false})
                                (reset! error? true))))]
              (div :css {:padding-bottom "12px"}
                (mostrar-texto texto-ou-elem)
                (codigo
                  (if code-before
                    (div :class "language-clojure" :dangerous-html (js/Prism.highlight (str/join "\n" code-before) js/Prism.languages.clojure "clojure"))
                    "")
                  (if (= subtype :area)
                    (resposta-usuario-area {:error? error?
                                            :user-result user-result
                                            :submit submit
                                            :input-value input-value
                                            :expected-code code-answer
                                            :code-template code-template
                                            :before code-before
                                            :after code-after})
                    (resposta-usuario-input {:error? error?
                                             :user-result user-result
                                             :submit submit
                                             :input-value input-value
                                             :expected-code code-answer
                                             :code-template code-template
                                             :before code-before
                                             :after code-after}))
                  (if code-after
                    (div :class "language-clojure" :dangerous-html (js/Prism.highlight (str/join "\n" code-after) js/Prism.languages.clojure "clojure"))
                    "")
                  (objetivo expected-result)
                  (seu-resultado user-result)
                  (h/when-tpl (j/cell= (not (:correct? user-result)))
                    (div :css {:display "flex"
                               :justify-content "flex-end"}
                      (div :css {:margin-right "10px"}
                        (h/button :css {:margin-right "10px"}
                          :click submit
                          "Executar")
                        (h/button :click #(reset! input-value (str/join "\n" code-template)) "Recomeçar")))))))]
      (h/when-dom d #(scroll-into-view d))
      d)))


(defn- content->elem
  [idx {:keys [type subtype description code-before code code-after code-reset button-label button-link button-callback code-template code-answer expected-result]}]
  (case type
    :sample (if description (mostrar-exemplo idx (pre-process-description description) (pre-process-code code)) (mostrar-exemplo idx (pre-process-code code)))
    :interactive-input (exemplo-interativo {:passo-inicial idx
                                            :texto-ou-elem (pre-process-description description)
                                            :code (pre-process-code-v code)})
    :interactive-area (exemplo-area-interativa {:passo-inicial idx
                                                :subtype (or subtype :area)
                                                :texto-ou-elem (pre-process-description description)
                                                :code-template (pre-process-code-v code-template)
                                                :code-answer (pre-process-code-v code-answer)
                                                :code-reset (pre-process-code-v code-reset)
                                                :code-before (pre-process-code-v code-before)
                                                :expected-result (pre-process-code-v expected-result)
                                                :code-after (pre-process-code-v code-after)})
    :button (h/when-tpl (j/cell= (>= step idx))
              (let [d (div
                        (md-str->elem (str/join "\n\n" (pre-process-description description)))
                        (let [b (h/button :click button-callback
                                  button-label)]
                          (h/when-dom b #(.focus b))
                          b))]
                (h/when-dom d #(scroll-into-view d))
                d))
    :link-button (h/when-tpl (j/cell= (>= step idx))
                   (let [d (div
                             (md-str->elem (str/join "\n\n" (pre-process-description description)))
                             (let [b (botao-link button-link button-label)]
                               (h/when-dom b #(.focus b))
                               b))]
                     (h/when-dom d #(scroll-into-view d))
                     d))))


(defn render-content
  [content]
  (apply div
    (map-indexed content->elem content)))


(defn lesson-description->view
  [{:keys [title intro content]}]
  (div
    (h/h1 title)
    (md-str->elem (str/join "\n\n" (pre-process-description intro)))
    (render-content content)))


(def teste
  {:title "Teste"
   :intro ["Teste"]
   :content [{:type :interactive-area
              :description ["Vamos fazer a nossa função retornar alguma coisa:"]
              :code-template ["(defn sigla"
                              "  [frase]"
                              "  "
                              ")"]
              :code-answer ["(defn sigla"
                            "  [frase]"
                            "  \"outra coisa\")"]
              :code-after  ["(sigla \"Unidos venceremos.\")"]
              :expected-result ["\"outra coisa\""]}]})


(defn link-simples
  [link texto]
  (h/a :href (str "#/" (name link)) texto))


(defn index
  []
  (div :css {:padding "0 24px"}
    (h/dialog :show-modal? show-modal?
      :css {:border-width "1px"}
      (let [repl-display (j/cell [])
            value (j/cell "")
            error? (j/cell nil)]
        (h/div
          :css {:width "80vw"}
          (h/h3 "REPL")
          (h/for-tpl [{:keys [input result]} repl-display]
            (h/div
              (h/div input)
              (h/div (j/cell= (pr-str result)))))
          (h/div
            (h/input :value value
              :autocomplete "off"
              :autocorrect "off"
              :spellcheck "false"
              :keyup #(let [expression @%]
                        (if (= "Enter" (.-key %))
                          (when-not (str/blank? expression)
                            (let [result (eval/code expression)]
                              (if (::eval/error result)
                                (reset! error? (update-keys result (comp keyword name)))
                                (do (swap! repl-display conj {:input expression :result result})
                                  (reset! error? nil)
                                  (reset! value "")))))
                          (reset! value expression)))))
          (h/div :toggle error?
            (h/div :css {:color "red"} (j/cell= (:error error?))))
          (h/button :click #(reset! show-modal? false)
            "Fechar"))))
    (h/case-tpl route
      :indice (div
                (h/h1 "Lições")
                (h/ul
                  (h/li (link-simples :intro "Intro"))
                  (h/li (link-simples :tipos-de-dados "Tipos"))
                  (h/li (link-simples :colecoes "Coleções"))
                  (h/li (link-simples :funcoes-1 (:title clojure/funcoes-1)))
                  (h/li (link-simples :funcoes-2 (:title clojure/funcoes-2)))
                  (h/li (link-simples :booleanos "Booleanos"))
                  (h/li (link-simples :strings "Strings"))
                  (h/li (link-simples :substrings "Substrings"))
                  (h/li (link-simples :numeros-1 (:title clojure/numeros-1)))
                  (h/li (link-simples :numeros-2 (:title clojure/numeros-2)))
                  (h/li (link-simples :numeros-3 (:title clojure/numeros-3)))
                  (h/li (link-simples :numeros-4 (:title clojure/numeros-4)))
                  (h/li (link-simples :definicoes "Definições"))
                  (h/li (link-simples :condicionais "Condicionais"))
                  (h/li (link-simples :sigla "Sigla"))))
      :intro (lesson-description->view clojure/introducao)
      :tipos-de-dados (lesson-description->view clojure/tipos-de-dados)
      :colecoes (lesson-description->view clojure/colecoes)
      :funcoes-1 (lesson-description->view clojure/funcoes-1)
      :funcoes-2 (lesson-description->view clojure/funcoes-2)
      :booleanos (lesson-description->view clojure/booleanos)
      :strings (lesson-description->view clojure/strings)
      :substrings (lesson-description->view clojure/substrings)
      :numeros-1 (lesson-description->view clojure/numeros-1)
      :numeros-2 (lesson-description->view clojure/numeros-2)
      :numeros-3 (lesson-description->view clojure/numeros-3)
      :numeros-4 (lesson-description->view clojure/numeros-4)
      :condicionais (lesson-description->view clojure/condicionais)
      :definicoes (lesson-description->view clojure/definicoes)
      :sigla (lesson-description->view clojure/sigla)
      :test (lesson-description->view teste))
    (debug)))


(defn log-fn
  [& params]
  (fn [_]
    (apply js/console.log params)))


(defonce match (j/cell nil))


(defn mudar-rota
  [rota]
  (reset! route rota)
  (reset! step 0))


(defn mudar-rota*
  [path]
  (mudar-rota (keyword (subs path 1))))


(def routes
  (rf/router
    ["/"
     ["" {:controllers [{:start #(do (reset! route :indice)
                                   (reset! step 0))}]}]
     ["intro" {:controllers [{:identity :path
                              :start mudar-rota*}]}]
     ["tipos-de-dados" {:controllers [{:identity :path
                                       :start mudar-rota*}]}]
     ["colecoes" {:controllers [{:identity :path
                                 :start mudar-rota*}]}]
     ["funcoes-1" {:controllers [{:identity :path
                                  :start mudar-rota*}]}]
     ["funcoes-2" {:controllers [{:identity :path
                                  :start mudar-rota*}]}]
     ["booleanos" {:controllers [{:identity :path
                                  :start mudar-rota*}]}]
     ["strings" {:controllers [{:identity :path
                                :start mudar-rota*}]}]
     ["substrings" {:controllers [{:identity :path
                                   :start mudar-rota*}]}]
     ["numeros-1" {:controllers [{:identity :path
                                  :start mudar-rota*}]}]
     ["numeros-2" {:controllers [{:identity :path
                                  :start mudar-rota*}]}]
     ["numeros-3" {:controllers [{:identity :path
                                  :start mudar-rota*}]}]
     ["numeros-4" {:controllers [{:identity :path
                                  :start mudar-rota*}]}]
     ["condicionais" {:controllers [{:identity :path
                                     :start mudar-rota*}]}]
     ["definicoes" {:controllers [{:identity :path
                                   :start mudar-rota*}]}]
     ["sigla" {:controllers [{:identity :path
                              :start mudar-rota*}]}]
     ["test" {:controllers [{:identity :path
                             :start mudar-rota*}]}]]
    {:data {:controllers [{:start (log-fn "start" "root-controller")
                           :stop (log-fn "stop" "root controller")}]}}))
