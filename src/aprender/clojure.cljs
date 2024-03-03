(ns aprender.clojure)


(defn lista-de-licoes
  [description]
  {:type :link-button
   :description description
   :button-label "Lista de lições"
   :button-link ""})


(def introducao
  {:title "Introdução"
   :intro ["No Ritmo de aprender você vai precisar ler textos e responder perguntas."]
   :content [{:type :sample
              :description ["O botão \"Executar código\" executa o código e mostra o resultado. Aperte o botão para ver o resultado:"]
              :code "(+ 1 1)"}
             {:type :interactive-input
              :description ["Em outros exercícios, vamos perguntar qual deve ser o resultado. Vou mostrar um exemplo simples."
                            "Você deve olhar o código e escrever qual você acha que vai ser o resultado no campo."
                            "Em seguida clique em \"Executar\" ou pressione a tecla Enter para ver se acertou."
                            "Não se preocupe caso não acerte de primeira, você está aqui para aprender e é esperado que possa ter dificuldades."]
              :code "(+ 2 2)"}
             (lista-de-licoes "Você já aprendeu o que vamos precisar para sua primeira lição.")]})


(def tipos-de-dados
  {:title "Tipos de dados"
   :intro ["Existem vários tipos de dados, cada um é útil em determinadas situações."
           "Aqui vamos apresentar alguns tipos que são bastante usados mas depois vamos nos aprofundar em cada um deles."]
   :content [{:type :sample
              :description ["## Booleanos"
                            "Booleanos são dados usados para dizer se algo é verdadeiro ou falso."
                            "Em Clojure temos dois valores que são booleanos: `true` para verdadeiro e `false` para falso."]
              :code "true"}
             {:type :interactive-input
              :description ["Sua vez:"]
              :code "false"}
             {:type :sample
              :description ["## Strings"
                            "Strings são dados usados para armazenar texto. Usamos aspas duplas `\"` para definir strings."
                            "Uma string sempre tem aspas duplas ao redor. Por exemplo:"]
              :code "\"algum texto\""}
             {:type :interactive-input
              :description "Tente você agora (não esqueça das aspas):"
              :code "\"outro texto\""}
             {:type :sample
              :description ["## Números inteiros"
                            "Números inteiros são usados para diversos tipos de cálculos, como índices em vetores, como identificadores e também para fazer contas. Por exemplo:"]
              :code "7"}
             {:type :interactive-input
              :description "Tente você agora. Note que números não possuem aspas ao redor:"
              :code "240"}
             {:type :sample
              :description "Strings podem conter números. É um texto que tem caracteres numéricos mas não é um número:"
              :code "\"240\""}
             {:type :interactive-input
              :description "Sua vez (não se esqueça das aspas):"
              :code "\"42\""}
             {:type :sample
              :description ["## Números decimais"
                            "Números decimais são usados para diversos tipos de cálculos. Você deve usar `.` como separador de valores decimais."]
              :code "3.14"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "6.28"}
             {:type :sample
              :description ["## Nada"
                            "Clojure tem um valor para representar o nada que é `nil`. Ele é usado para quando um valor não é encontrado"]
              :code "nil"}
             {:type :interactive-input
              :description "Pratique:"
              :code "nil"}
             {:type :sample
              :description "Clojure tem um dado chamado keyword que serve como identificador de atributos em mapas, ou outros tipos de identificadores. Keywords começam com `:`. Por exemplo:"
              :code ":nome-completo"}
             {:type :interactive-input
              :description "Sua vez:"
              :code ":sobrenome"}
             (lista-de-licoes "Você já aprendeu alguns tipos de dados básicos, pode ir para outra lição.")]})


(def colecoes
  {:title "Coleções de Dados"
   :intro "Existem vários tipos de coleções de dados em Clojure, aqui vamos apresentar a principais."
   :content [{:type :sample
              :description ["Vetores são coleções de valores ordenados e indexados por inteiros. Eles são representados por colchetes `[]`."
                            "Seguem alguns exemplos. Um vetor vazio:"]
              :code "[]"}
             {:type :sample
              :description ["Vetor contendo o número `1`:"]
              :code "[1]"}
             {:type :sample
              :description "Vetor contendo vários números"
              :code "[1 3 5 7 9]"}
             {:type :sample
              :description "Vetor contendo vários dados de tipos diferentes. Veja que um vetor pode estar dentro de outro vetor."
              :code "[1 \"z\" :a []]"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "[:z]"}
             {:type :interactive-input
              :code "[0 2 4 6 8]"}
             {:type :interactive-input
              :code "[nil true 1]"}
             {:type :sample
              :description ["Mapas são coleções que mapeiam chaves para valores. Eles são representados por chaves `{}`."
                            "Seguem alguns exemplos. Um mapa vazio:"]
              :code "{}"}
             {:type :sample
              :description "Mapa contendo a chave `:nome` e o valor `\"Joana\"`:"
              :code "{:nome \"Joana\"}"}
             {:type :sample
              :description "Mapa anterior mais a chave `:idade` e o valor `21`:"
              :code "{:nome \"Joana\", :idade 21}"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "{:nome \"Alice\"}"}
             {:type :interactive-input
              :code "{:nome \"Alice\" :idade 34}"}
             {:type :interactive-input
              :code "{}"}
             {:type :sample
              :description ["Conjuntos são coleções de valores únicos. Eles são representados por chaves depois de um hash `#{}`."
                            "Seguem alguns exemplos. Um conjunto vazio:"]
              :code "#{}"}
             {:type :sample
              :description "Conjunto contendo o número `1`:"
              :code "#{1}"}
             {:type :sample
              :description "Conjunto contendo vários elementos:"
              :code "#{true nil 42}"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "#{0 1}"}
             {:type :interactive-input
              :code "#{:abacate :banana}"}
             (lista-de-licoes "Agora que você já aprendeu algumas coleções de dados, pode ir para outra lição.")]})


(def funcoes-1
  {:title "Introdução a Funções"
   :description "Funções são código que alguém criou que você pode usar. Linguagens de programação normalmente já vem com muitas funções bastante úteis."
   :content [{:type :sample
              :description "Usar uma função é simples. Veja um exemplo usando a função `count` que conta os caracteres de uma string ou os elementos de uma coleção:"
              :code "(count \"pedra\")"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "(count \"gato\")"}
             {:type :sample
              :description "Um exemplo usando um vetor:"
              :code "(count [1 2 3 4 5 6])"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "(count [3 2 1])"}
             {:type :sample
              :description ["Nós usamos parenteses para executar uma função."
                            "Dentro dos parenteses a primeira coisa que vem é o nome da função e em seguida os argumentos da função."
                            "No exemplo a seguir `str` é a função, enquanto `1`, `\"a\"` e `true` são os argumentos:"]
              :code "(str 1 \"a\" true)"}
             {:type :interactive-input
              :description ["Funções podem não precisar de argumentos, como no caso da própria `str`."
                            "Ela retorna uma string vazia quando executada sem argumentos:"]
              :code "(str)"}
             {:type :sample
              :description ["O argumento de uma função pode ser o resultado de outra função."]
              :code "(str (inc 1))"}
             {:type :sample
              :description ["Isso pode ser recursivo, ou seja, o argumento de uma função pode ser o resultado de outra função que pode ter como argumento o resultado de outra função e assim por diante."]
              :code "(str (* (inc (inc 1)) 10 ))"}
             {:type :interactive-input
              :description ["A função `dec` subtrai 1 do seu argumento."
                            "Sua vez:"]
              :code "(str (* (dec (dec 3)) 100 ))"}
             (lista-de-licoes "Você aprendeu o básico de como usar funções, pode seguir para outra lição.")]})


(def funcoes-2
  {:title "Deixando chamadas de funções mais legíveis"
   :description "Quanto temos uma função que recebe o resultado de outra como argumento que recebe o resultado de outra como argumento as vezes não fica muito legível."
   :content [{:type :sample
              :description ["Em um dos exemplos que já vimos temos o seguinte código:"]
              :code "(str (* (inc (inc 1)) 10))"}
             {:type :sample
              :description ["Podemos reescrever usando `->`"]
              :code ["(-> 1 inc inc (* 10) str)"]}
             {:type :sample
              :description ["Separando cada operação em uma linha fica mais fácil de ler:"]
              :code ["(-> 1"
                     "    inc"
                     "    inc"
                     "    (* 10)"
                     "    str)"]}
             (lista-de-licoes "Você aprendeu o básico de como deixar chamadas de funções mais legíveis, pode seguir para outra lição.")]})


(def booleanos
  {:title "Booleanos"
   :description "Booleanos são dados usados para dizer se algo é verdadeiro ou falso."
   :content [{:type :sample
              :description ["Em Clojure temos dois valores que são booleanos: `true` para verdadeiro e `false` para falso."]
              :code "false"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "true"}
             {:type :sample
              :description ["A função `not` serve para \"inverter\" um booleano. Veja: "]
              :code "(not false)"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "(not true)"}
             {:type :sample
              :description ["Em Clojure a função `=` serve para sabermos se os argumentos passados para ela são iguais ou diferentes."
                            "Será que o número `2` e a string `\"2\"` são iguais?"]
              :code "(= 2 \"2\")"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "(= 1 3)"}
             {:type :interactive-input
              :code "(= :a :a)"}
             {:type :interactive-input
              :code "(= :a \"a\")"}
             {:type :sample
              :description ["Temos a função `not=` que serve para sabermos se os argumentos passados para ela são diferentes ou iguais."
                            "Será que o número `2` e a string `\"2\"` são diferentes?"]
              :code "(not= 2 \"2\")"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "(not= 1 3)"}
             {:type :interactive-input
              :code "(not= :a :a)"}
             {:type :sample
              :description "Temos também a função `true?` que retorna `true` se o argumento for `true` e `false` caso contrário:"
              :code "(true? true)"}
             {:type :interactive-input
              :code "(true? false)"}
             {:type :interactive-input
              :code "(true? nil)"}
             {:type :interactive-input
              :code "(true? 8)"}
             {:type :interactive-input
              :code "(true? \"true\")"}
             {:type :interactive-input
              :description ["A função `false?` funciona da mesma forma, só que só retorna `true` se o argumento recebido for `false`."]
              :code "(false? false)"}
             {:type :interactive-input
              :code "(false? true)"}
             {:type :interactive-input
              :code "(false? nil)"}
             {:type :interactive-input
              :code "(false? :abc)"}
             {:type :sample
              :description ["Podemos também descobrir se um argumento é uma string. A função `string?` retorna `true` se o argumento dela for uma string e `false` caso não seja."]
              :code "(string? 2)"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "(string? \"abacate\")"}
             {:type :interactive-input
              :code "(string? :abacaxi)"}
             (lista-de-licoes "Você aprendeu um pouco sobre booleanos, pode ir para outra lição.")]})


(def strings
  {:title "Strings"
   :description "Strings são muito importantes em programaçao. Quando você recebe um e-mail, uma mensagem de texto, preenche um formulário está usando strings."
   :content [{:type :sample
              :description "Elas são um tipo de dado usado para armazenar textos. Em Clojure usamos aspas duplas para definir strings. Por exemplo:"
              :code "\"abelha\""}
             {:type :interactive-input
              :description "Tente você agora (não esqueça das aspas):"
              :code "\"amora\""}
             {:type :interactive-input
              :description ["Note que \"Uva\" e \"uva\" não são iguais. Letras minúsculas e maiúsculas são diferentes"]
              :code "\"Uva\""}
             {:type :sample
              :description ["Em Clojure existem várias funções para trabalharmos com strings. A mais usada provavelmente é `str`."
                            "Ela pode receber vários argumentos e retorna uma string que junta todos os argumentos na ordem que eles foram passados."
                            "Por exemplo, podemos passar um número como argumento:"]
              :code "(str 42)"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "(str 21)"}
             {:type :sample
              :description ["`str` pode ser executada sem argumentos. Nesse caso ela retorna uma string vazia:"]
              :code "(str)"}
             {:type :interactive-input
              :description ["`str` pode receber um valor especial que representa o nada chamado `nil`. Neste caso ela retorna uma string vazia também."
                            "Tente você:"]
              :code "(str nil)"}
             {:type :interactive-input
              :description ["Quando você passa uma string para `str`, ela vai retornar a própria string."
                            "Tente você:"]
              :code "(str \"já sou uma string\")"}
             {:type :sample
              :description ["Mas normalmente vamos passar vários argumentos para `str`, e ela vai transformar os argumentos em strings e juntar tudo em uma única string."
                            "Veja:"]
              :code "(str \"Maria tem \" 10 \" anos.\")"}
             {:type :sample
              :description "Perceba que espaços em branco são importantes. Note a diferença entre:"
              :code "(str \"Céu\" \"azul\")"}
             {:type :sample
              :description "E (observe que tem um espaço no início da segunda string):"
              :code "(str \"Céu\" \" azul\")"}
             {:type :sample
              :description "Outra opção (uma nova string com apenas um espaço):"
              :code "(str \"Céu\" \" \" \"azul\")"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "(str \"Terra\" \"vermelha\")"}
             {:type :interactive-input
              :code "(str \"Terra\" \" \" \"vermelha\")"}
             {:type :sample
              :description ["Também podemos contar os caracteres de uma string usando `count`:"]
              :code "(count \"Céu\")"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "(count \"Maçã\")"}

             (lista-de-licoes "Você aprendeu um pouco sobre strings, pode ir para outra lição.")]})


(def substrings
  {:title "Substrings"
   :description "Substrings são bastante usadas para encontrar apenas uma parte de uma string usando a posição dos caracteres"
   :content [{:type :sample
              :description ["Também podemos criar uma nova string que é apenas uma parte da string original usando `subs`."
                            "`subs` pode receber 2 ou 3 argumentos. Vamos ver primeiro o caso de 2 argumentos."
                            "Neste caso o primeiro argumento deve ser uma string e o segundo um número inteiro. O número inteiro é o índice do caractere inicial."
                            "Em programação contamos a partir do número `0`. Então o primeiro caractere de uma string tem o índice `0`. O segundo caractere tem o índice `1` e assim por diante."
                            "Então se quisermos uma substring que começa no caractere de índice `0` vamos ter a própria string:"]
              :code "(subs \"dado\" 0)"}
             {:type :sample
              :description "Mas se passarmos o índice inicial como 1 temos:"
              :code "(subs \"dado\" 1)"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "(subs \"dado\" 2)"}
             {:type :interactive-input
              :code "(subs \"dado\" 3)"}
             {:type :interactive-input
              :code "(subs \"dado\" 4)"}
             {:type :sample
              :description ["Vamos ver agora o caso de 3 argumentos para subs."
                            "O primeiro argumento continua sendo uma string e o segundo continua sendo um número inteiro que é o índice do caractere inicial."
                            "O terceiro argumento é o índice do caractere final mas o caractere que está no índice final não vai fazer parte da substring."
                            "A explicação pode parecer complicada, mas exemplos vão deixar isso mais claro"
                            "Podemos obter a própria string passando 0 como índice inicial e o tamanho da string como índice final:"]
              :code "(subs \"abelha\" 0 6)"}
             {:type :sample
              :description ["No exemplo acima o índice do último a da palavra abelha é `5` (lembre-se que começamos a contar do `0`)."
                            "Vamos ver agora o que acontece quando passamos `5` como índice final:"]
              :code "(subs \"abelha\" 0 5)"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "(subs \"abelha\" 0 4)"}
             {:type :interactive-input
              :code "(subs \"abelha\" 0 3)"}
             {:type :interactive-input
              :code "(subs \"abelha\" 0 2)"}
             {:type :interactive-input
              :code "(subs \"abelha\" 0 1)"}
             {:type :interactive-input
              :code "(subs \"abelha\" 0 0)"}
             {:type :sample
              :description "Então podemos criar uma substring com qualquer parte da string:"
              :code "(subs \"abelha\" 1 5)"}
             {:type :interactive-input
              :description "Sua vez:"
              :code "(subs \"abelha\" 1 4)"}
             (lista-de-licoes "Você aprendeu um pouco sobre strings, pode ir para outra lição.")]})


(def numeros-1
  {:title "Números - Operações básicas"
   :intro "Números são muito importantes em programação. Eles servem como preços em lojas online, para somar todas as compras que você fez, para calcular parcelas e juros, somar o frete, calcular descontos."
   :content [{:type :sample :description "Para somar números usamos a função `+`:" :code "(+ 1 3)"}
             {:type :sample :description "`+` pode receber apenas um argumento númerico e nesse caso retorna o próprio número:" :code "(+ 3)"}
             {:type :sample :description "`+` pode receber zero argumentos e nesse caso retorna `0`:" :code "(+)"}
             {:type :interactive-input :description "Sua vez:" :code "(+ 1 2 3 4)"}
             {:type :interactive-input :code "(+ 1.5 2)"}
             {:type :interactive-input :code "(+ 225)"}
             {:type :interactive-input :code "(+)"}
             {:type :sample :description "Para subtrair números usamos a função `-`:" :code "(- 3 1)"}
             {:type :sample :description "`-` pode receber apenas um argumento númerico e nesse caso retorna o número oposto, isto é, o negativo do número:" :code "(- 3)"}
             {:type :interactive-input :description "Sua vez:" :code "(- 6 3 2)"}
             {:type :interactive-input :code "(- 6 2)"}
             {:type :interactive-input :code "(- -25)"}
             {:type :sample :description "Para multiplicar números usamos a função `*`:" :code "(* 3 2)"}
             {:type :sample :description "`*` pode receber apenas um argumento númerico e nesse caso retorna o próprio número:" :code "(* 3)"}
             {:type :sample :description "`*` pode receber zero argumentos e nesse caso retorna `1`:" :code "(*)"}
             {:type :interactive-input :description "Sua vez:" :code "(* 2 2 2)"}
             {:type :interactive-input :code "(* 42)"}
             {:type :interactive-input :code "(*)"}
             {:type :interactive-input :code "(* 0 25 89 23)"}
             {:type :sample :description "Para dividir números usamos a função `/`:" :code "(/ 4 2)"}
             {:type :sample :description "`/` pode receber apenas um argumento númerico e nesse caso retorna o inverso do número:" :code "(/ 2)"}
             {:type :interactive-input :description "Sua vez:" :code "(/ 4 2 2)"}
             {:type :interactive-input :code "(/ 9 3)"}
             {:type :interactive-input :code "(/ 10)"}
             {:type :interactive-input :description "`inc` incrementa um número, ou seja soma 1 ao número. `dec` decrementa um número, isto é, subtrai 1 do número:" :code "(inc 0)"}
             {:type :interactive-input :code "(inc 20)"}
             {:type :interactive-input :code "(dec 0)"}
             {:type :interactive-input :code "(dec 100)"}
             (lista-de-licoes "Você aprendeu um pouco sobre números, pode ir para outra lição.")]})


(def numeros-2
  {:title "Números - Mais operações e comparação"
   :intro "Vamos agora comparar números, ver como encontrar o máximo e mímino entre números."
   :content [{:type :interactive-input :description ["Podemos comparar números, por exemplo para saber qual produto é mais barato."
                                                     "Temos as funções `<` (menor), `<=` (menor ou igual), `=` (igual), `>=` (maior ou igual) e `>` (maior) que são usadas para comparar números."
                                                     "Todas elas retornam booleanos (`true` ou `false`)"] :code "(< 0 1)"}
             {:type :interactive-input :code "(< 200 100)"}
             {:type :interactive-input :code "(< 100 100)"}
             {:type :interactive-input :code "(<= 100 100)"}
             {:type :interactive-input :code "(<= 100 200)"}
             {:type :interactive-input :code "(<= 200 100)"}
             {:type :interactive-input :code "(= 200 100)"}
             {:type :interactive-input :code "(= 100 100)"}
             {:type :interactive-input :code "(>= 100 100)"}
             {:type :interactive-input :code "(>= 100 200)"}
             {:type :interactive-input :code "(>= 200 100)"}
             {:type :interactive-input :code "(> 200 100)"}
             {:type :interactive-input :code "(> 100 100)"}
             {:type :interactive-input :code "(> 100 400)"}
             {:type :interactive-input :description "Todos eles podem ser usados com mais argumentos:" :code "(> 400 300 200)"}
             {:type :interactive-input :code "(> 400 300 200 201)"}
             {:type :sample :description "Para encontrar o máximo entre números usamos a função `max`:" :code "(max 2 4 9 3)"}
             {:type :interactive-input :code "(max 3)"}
             {:type :interactive-input :code "(max -20 -4 -53)"}
             {:type :interactive-input :code "(max -2000 1)"}
             {:type :interactive-input :description "Você deve imaginar o que a função `min` faz:" :code "(min 1 89 23)"}
             {:type :interactive-input :code "(min -89 -25 0)"}
             {:type :interactive-input :code "(min -89 -200)"}
             (lista-de-licoes "Você aprendeu um pouco sobre números, pode ir para outra lição.")]})


(def numeros-3
  {:title "Números - Predicados"
   :intro "Temos funções especiais para alguns casos comuns. Vamos aprender alguns deles:"
   :content [{:type :interactive-input :description ["Algumas comparações tem funções próprias:"
                                                     "- `zero?` retorna `true` se o argumento for `0` e `false` caso contrário."
                                                     "- `pos?` retorna `true` se o argumento for maior que `0` e `false` caso contrário."
                                                     "- `neg?` retorna `true` se o argumento for menor que `0` e `false` caso contrário."
                                                     "- `even?` retorna `true` se o argumento for um número par e `false` caso contrário."
                                                     "- `odd?` retorna `true` se o argumento for um número impar e `false` caso contrário."] :code "(zero? (- 3 (+ 1 2)))"}
             {:type :interactive-input :code "(zero? 400)"}
             {:type :interactive-input :code "(pos? 400)"}
             {:type :interactive-input :code "(pos? 0)"}
             {:type :interactive-input :code "(pos? -4)"}
             {:type :interactive-input :code "(neg? 400)"}
             {:type :interactive-input :code "(neg? 0)"}
             {:type :interactive-input :code "(neg? -4)"}
             {:type :interactive-input :code "(even? -4)"}
             {:type :interactive-input :code "(even? 0)"}
             {:type :interactive-input :code "(even? 3)"}
             {:type :interactive-input :code "(odd? 7)"}
             {:type :interactive-input :code "(odd? 2)"}
             (lista-de-licoes "Você aprendeu um pouco sobre números, pode ir para outra lição.")]})


(def numeros-4
  {:title "Números - Arredondamento"
   :intro "Em muitas situações precisamos arredondar um número."
   :content [{:type :interactive-input :description ["Vamos aprender sobre arredondamento:"
                                                     "- `math/round` retorna o número inteiro mais próximo. Se ficar no meio do caminho entre dois números inteiros, ele retorna o maior deles."
                                                     "- `math/floor` (piso) retorna o número inteiro mais próximo, igual ou menor ao argumento."
                                                     "- `math/ceil` (teto) retorna o número inteiro mais próximo, igual ou maior ao argumento."]
              :code "(math/round 1.1)"}
             {:type :interactive-input :code "(math/round 2.5)"}
             {:type :interactive-input :code "(math/round 2.9)"}
             {:type :interactive-input :code "(math/round 2)"}
             {:type :interactive-input :code "(math/floor 2)"}
             {:type :interactive-input :code "(math/floor 2.5)"}
             {:type :interactive-input :code "(math/floor 2.99)"}
             {:type :interactive-input :code "(math/ceil 2)"}
             {:type :interactive-input :code "(math/ceil 2.01)"}
             {:type :interactive-input :code "(math/ceil 2.90)"}
             (lista-de-licoes "Você aprendeu um pouco sobre números, pode ir para outra lição.")]})


(def condicionais
  {:title "Condicionais"
   :intro "Condicionais permitem que o programa faça escolhas de acordo com os dados recebidos."
   :content [{:type :sample
              :description ["## if"
                            "O condicional mais usado é `if`. `if` significa \"se\" em português."
                            "O jeito de usar é `(if teste então senão)`. Se `teste` não for `false` ou `nil` o resultado vai ser `então`."
                            "Já se `teste` for `false` ou `nil` o resultado vai ser `senão`."
                            "Nesse exemplo se o tempo-hoje for chuva vamos ficar em casa. Se for sol, vamos fazer um picnic."]
              :code ["(def tempo-hoje :chuva)"
                     ""
                     "(if (= :chuva tempo-hoje)"
                     "  :ficar-em-casa"
                     "  :fazer-picnic)"]}
             {:type :interactive-input
              :code ["(def tempo-hoje :sol)"
                     ""
                     "(if (= :chuva tempo-hoje)"
                     "  :ficar-em-casa"
                     "  :fazer-picnic)"]}
             {:type :interactive-input
              :description ["Lembre-se que `even` significa par e `odd` significa impar."]
              :code ["(def numero 4)"
                     ""
                     "(if (even? numero)"
                     "  :par"
                     "  :impar)"]}
             {:type :interactive-input
              :code ["(def teste true)"
                     ""
                     "(if teste"
                     "  :primeiro"
                     "  :segundo)"]}
             {:type :interactive-input
              :code ["(def teste false)"
                     ""
                     "(if teste"
                     "  :primeiro"
                     "  :segundo)"]}
             {:type :interactive-input
              :code ["(def numero 5)"
                     ""
                     "(if (even? numero)"
                     "  :par"
                     "  :impar)"]}
             {:type :interactive-input
              :code ["(def teste true)"
                     ""
                     "(if teste"
                     "  :teste-verdadeiro"
                     "  :teste-falso)"]}
             {:type :interactive-input
              :description "Para o teste tudo é verdadeiro menos `false` e `nil`."
              :code ["(if 99"
                     "  :teste-verdadeiro"
                     "  :teste-falso)"]}
             {:type :interactive-input
              :code ["(if :a"
                     "  4"
                     "  8)"]}
             {:type :sample :description ["As vezes não tem `senão`: `(if teste então)`. Se `teste` não for `false` ou `nil` o resultado vai ser `então`."
                                          "Já se `teste` for `false` ou `nil` o resultado vai ser `nil` neste caso."]
              :code ["(if true"
                     "  4)"]}
             {:type :interactive-input
              :code ["(if nil"
                     "  4)"]}
             {:type :interactive-input
              :code ["(if false"
                     "  4)"]}
             {:type :sample :description ["`teste` normalmente não é um valor fixo, senão seria mais simples só colocar o valor resultado do `if` diretamente."]
              :code ["(if (pos? -1)"
                     "  :positivo"
                     "  :negativo-ou-zero)"]}
             {:type :interactive-input
              :code ["(if (pos? 0)"
                     "  :positivo"
                     "  :negativo-ou-zero)"]}
             {:type :interactive-input
              :code ["(if (pos? 21)"
                     "  :positivo"
                     "  :negativo-ou-zero)"]}
             (lista-de-licoes "Você aprendeu um pouco sobre condicionais, pode ir para outra lição.")]})


(def definicoes
  {:title "Definições"
   :intro "Definições permitem que o usuário defina valores para usar em outras partes do programa."
   :content [{:type :sample
              :description ["Usamos a expressão `(def um-nome um-valor)` para dar ao valor `um-valor` o nome `um-nome`."]
              :code ["(def nome \"Maria\")"
                     "nome"]}
             {:type :interactive-input
              :description "Sua vez:"
              :code ["(def nome \"Dante\")"
                     "nome"]}
             {:type :interactive-area
              :subtype :input
              :code-template [""]
              :code-answer ["(def nome \"Erika\")"]
              :code-reset ["(ns-unmap (find-ns 'user) 'nome)"]
              :code-after  ["nome"]
              :expected-result ["\"Erika\""]}
             {:type :sample
              :description "Podemos definir valores que são qualquer tipo:"
              :code ["(def ano 2024)"
                     "ano"]}
             {:type :interactive-input
              :description "Ou pode ser o resultado de uma função:"
              :code ["(def cinco-ao-quadrado (* 5 5))"
                     "cinco-ao-quadrado"]}
             (lista-de-licoes "Você aprendeu um pouco sobre definições, pode ir para outra lição.")]})


(def sigla
  {:title "Sigla"
   :intro ["Uma sigla é uma palavra formada pela inicial de cada palavra de uma frase."
           "Vamos aprender a escrever uma função que converte uma frase em uma sigla."
           "Para isso precisamos saber como escrever uma função que recebe um argumento (a frase) e retorna uma string que contém a sigla resultante."
           "Alguns exemplos:"
           "- \"Unidos venceremos.\": \"UV\""
           "- \"A união faz a força.\": \"AUFAF\""
           "- \"Uma frase qualquer.\": \"UFQ\""
           "Para isso vamos precisar aprender a:"
           "- Escrever uma função."
           "- Pegar a primeira letra de cada palavra."
           "- Criar uma string com essas letras."
           "- Converter em letras maiúsculas."]
   :content [{:type :sample
              :description ["## Escrever uma função"
                            "Para escrever uma função usamos `(defn nome-da-funcao [argumento-da-funcao] :corpo-da-funcao)`."
                            "Separando as partes em várias linhas fica mais fácil de enxergar."]
              :code ["; isto é um comentário"
                     "; em todas as linhas o que vem depois de `;` é ignorado no programa."
                     "; vamos usar comentários para explicar o que cada parte da função faz"
                     "(defn sigla ; este é o nome da função."
                     "  [frase] ; neste vetor vem os argumentos (neste caso apenas: frase)"
                     "  ; o que a função faz fica aqui (por enquanto nada)"
                     ")"
                     ""
                     "; e agora sem os comentários para ficar mais fácil de ler"
                     "(defn sigla"
                     "  [frase]"
                     ")"
                     ""
                     "(sigla \"Unidos venceremos.\")"]}
             {:type :sample
              :description ["Vamos fazer a nossa função retornar alguma coisa:"]
              :code ["(defn sigla"
                     "  [frase]"
                     "  \"alguma coisa\")"
                     ""
                     "(sigla \"Unidos venceremos.\")"]}
             {:type :interactive-area
              :description ["Vamos fazer a nossa função retornar outra coisa:"]
              :code-template ["(defn sigla"
                              "  [frase]"
                              "  "
                              ")"]
              :code-answer ["(defn sigla"
                            "  [frase]"
                            "  \"outra coisa\")"]
              :code-after  ["(sigla \"Unidos venceremos.\")"]
              :expected-result ["\"outra coisa\""]}
             {:type :interactive-area
              :description ["Vamos fazer a nossa função retornar seu próprio argumento:"]
              :code-template ["(defn sigla"
                              "  [frase]"
                              "  "
                              ")"]
              :code-answer ["(defn sigla"
                            "  [frase]"
                            "  frase)"]
              :code-after  ["(sigla \"Unidos venceremos.\")"]
              :expected-result ["\"Unidos venceremos.\""]}
             {:type :interactive-area
              :description ["Vamos fazer a nossa função contar os caracteres do seu argumento:"]
              :code-template ["(defn sigla"
                              "  [frase]"
                              "  "
                              ")"]
              :code-answer ["(defn sigla"
                            "  [frase]"
                            "  (count frase))"]
              :code-after  ["(sigla \"Unidos\")"]
              :expected-result ["6"]}
             {:type :sample
              :description ["Agora vamos aprender a separar as palavras de uma frase:"]
              :code ["(str/split \"Unidos venceremos.\" #\" \")"]}
             {:type :interactive-input
              :description ["Sua vez:"]
              :code ["(str/split \"Adoro abacaxi\" #\" \")"]}
             {:type :interactive-area
              :description ["Agora vamos separar as palavras da nossa frase"]
              :code-template ["(defn sigla"
                              "  [frase]"
                              "  frase"
                              ")"]
              :code-answer ["(defn sigla"
                            "  [frase]"
                            "  (str/split frase #\" \"))"]
              :code-after  ["(sigla \"Que legal\")"]
              :expected-result ["[\"Que\" \"legal\"]"]}
             {:type :sample
              :description ["E como pegar a primeira letra de uma palavra?"]
              :code ["(first \"Unidos\")"]}
             {:type :interactive-input
              :description ["Tente você:"]
              :code ["(first \"Banana\")"]}
             {:type :sample
              :description ["E como eu uso isso em uma coleção?"]
              :code ["(mapv first [\"Unidos\" \"venceremos.\"])"]}
             {:type :interactive-input
              :description ["Sua vez:"]
              :code ["(mapv first [\"Adoro\" \"abacaxi\" \"doce\"])"]}
             {:type :interactive-area
              :description ["Agora podemos criar a nossa sigla. Vamos fazer um passo de cada vez começando de onde estávamos."
                            "Tínhamos separado em palavras. O próximo passo é pegar a primeira letra de cada uma delas:"]
              :code-template ["(defn sigla"
                              "  [frase]"
                              "  (str/split frase #\" \")"
                              ")"]
              :code-answer ["(defn sigla"
                            "  [frase]"
                            "  (mapv first"
                            "    (str/split frase #\" \")))"]
              :code-after  ["(sigla \"Que legal\")"]
              :expected-result ["[\"Q\" \"l\"]"]}
             {:type :sample
              :description ["E para juntar uma coleção de strings em uma única strings fazemos:"]
              :code ["(str/join [\"U\" \"v\"])"]}
             {:type :interactive-input
              :description ["Sua vez:"]
              :code ["(str/join [\"A\" \"a\" \"d\"])"]}
             {:type :interactive-area
              :description ["Em seguida vamos juntar as letras em uma só string:"]
              :code-template ["(defn sigla"
                              "  [frase]"
                              "  (mapv first"
                              "    (str/split frase #\" \")))"]
              :code-answer ["(defn sigla"
                            "  [frase]"
                            "  (str/join"
                            "    (mapv first"
                            "      (str/split frase #\" \"))))"]
              :code-after  ["(sigla \"Que legal\")"]
              :expected-result ["\"Ql\""]}
             {:type :sample
              :description ["Para deixar todas as letras de uma string maiúsculas:"]
              :code ["(str/upper-case \"Uv\")"]}
             {:type :interactive-input
              :description ["Tente você"]
              :code ["(str/upper-case \"Aad\")"]}
             {:type :interactive-area
              :description ["Agora vamos transformar a string para maiúscula e terminar nossa função:"]
              :code-template ["(defn sigla"
                              "  [frase]"
                              "  (str/join"
                              "    (mapv first"
                              "      (str/split frase #\" \"))))"]
              :code-answer ["(defn sigla"
                            "  [frase]"
                            "  (str/upper-case"
                            "    (str/join"
                            "      (mapv first"
                            "        (str/split frase #\" \")))))"]
              :code-after  ["(sigla \"Que legal\")"]
              :expected-result ["\"QL\""]}]})
