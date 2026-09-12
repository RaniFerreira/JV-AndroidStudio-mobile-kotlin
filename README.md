# Jogo da Velha (Tic-Tac-Toe) — Android

Aplicativo Android nativo (Kotlin + XML Views) que implementa o clássico Jogo da Velha para dois jogadores no mesmo aparelho.

## Funcionalidades

- Tabuleiro 3x3 construído com `GridLayout`.
- `TextView` de status exibindo de quem é a vez, quem venceu ou se houve empate.
- Início sempre pelo jogador **X**.
- Validação de vitória em todas as linhas, colunas e diagonais, e detecção de empate.
- Células já marcadas ficam bloqueadas e, ao final da partida (vitória ou empate), o tabuleiro inteiro é desativado.
- Cores distintas para cada símbolo (X em azul, O em vermelho).
- Botão **Reiniciar Jogo**, que limpa apenas o tabuleiro.

### Desafios técnicos implementados

- **Desafio 1 — UI e Feedback:** as três células da combinação vencedora são destacadas em verde e recebem uma animação de pulso (`res/anim/win_pulse.xml`) ao final da partida.
- **Desafio 2 — Gestão de Estado:** um placar de sessão (vitórias de X, vitórias de O e empates) é mantido em `MainActivity` e **não é zerado** ao reiniciar o tabuleiro — apenas volta a zero quando o app é totalmente encerrado. O estado também é preservado em rotações de tela via `onSaveInstanceState`.

## Capturas de tela

Coloque os prints do app na pasta `docs/screenshots/` usando os nomes abaixo (ou ajuste os nomes/legendas conforme suas imagens):

| Tela inicial | Partida em andamento | Vitória | Empate |
|---|---|---|---|
| ![Tela inicial](docs/screenshots/tela-inicial.png) | ![Partida em andamento](docs/screenshots/em-andamento.png) | ![Vitória](docs/screenshots/vitoria.png) | ![Empate](docs/screenshots/empate.png) |

> Se uma imagem ainda não existir na pasta, o GitHub mostra a célula quebrada — basta adicionar o arquivo `.png` com o nome correspondente que a imagem aparece automaticamente.

## Estrutura principal

```
app/src/main/java/com/example/myjv/MainActivity.kt   # Toda a lógica do jogo
app/src/main/res/layout/activity_main.xml             # Tela (GridLayout 3x3 + status + placar + botão)
app/src/main/res/values/                               # strings, cores e estilos (CellButton)
app/src/main/res/anim/win_pulse.xml                    # Animação da combinação vencedora
```

## Pré-requisitos

- [Android Studio](https://developer.android.com/studio) (recomendado: versão mais recente).
- JDK 11+ (o Android Studio já inclui um JDK compatível).
- Android SDK com a plataforma **API 37** instalada (o próprio Android Studio oferece para baixar automaticamente na primeira sincronização, caso não esteja instalada).

## Como compilar e executar

### Opção 1 — Android Studio (recomendado)

1. Abra o Android Studio e escolha **Open** (ou **File > Open**).
2. Selecione a pasta raiz deste projeto.
3. Aguarde a sincronização do Gradle (o Android Studio baixa as dependências e, se necessário, a plataforma SDK 37).
4. Conecte um dispositivo físico (com depuração USB habilitada) ou inicie um emulador (**Tools > Device Manager**).
5. Clique em **Run ▶** (ou `Shift + F10`) para instalar e executar o app.

### Opção 2 — Linha de comando (Gradle Wrapper)

No terminal, dentro da pasta raiz do projeto:

```bash
# Compilar o APK de debug
./gradlew assembleDebug        # Linux/macOS
gradlew.bat assembleDebug      # Windows

# Instalar em um dispositivo/emulador já conectado (adb devices)
./gradlew installDebug         # Linux/macOS
gradlew.bat installDebug       # Windows
```

O APK gerado fica em `app/build/outputs/apk/debug/app-debug.apk`.

## Como jogar

1. O jogador **X** sempre começa.
2. Toque em uma célula vazia para marcá-la; os jogadores se alternam automaticamente a cada jogada.
3. Ao formar uma linha, coluna ou diagonal, a vitória é anunciada, as células vencedoras são destacadas em verde com animação, o tabuleiro é bloqueado e o placar é atualizado.
4. Se todas as células forem preenchidas sem um vencedor, é declarado empate.
5. Toque em **Reiniciar Jogo** para limpar o tabuleiro e jogar novamente — o placar da sessão é mantido.
