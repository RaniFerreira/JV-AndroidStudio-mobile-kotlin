# Jogo da Velha  — Android

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


| Tela inicial | Partida em andamento | Vitória | Empate |
|:---:|:---:|:---:|:---:|
| <img width="180" height="350" src="https://github.com/user-attachments/assets/d692ab1d-8581-4fad-8b68-9448d9913102" /> | <img width="180" height="350" src="https://github.com/user-attachments/assets/f0e3a28d-97c3-43cf-ae4f-31ceb6a1fc7c" /> | <img width="180" height="350" src="https://github.com/user-attachments/assets/a121da2e-ddbf-445d-bda5-a1fdfaa2a248" /> | <img width="180" height="350" src="https://github.com/user-attachments/assets/c630c5e9-8e4f-48e4-ae82-728600ff0eed" /> |

