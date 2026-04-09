<div align="center">

# 🛰️ Space Radar

**API Java/Spring que monitora Objetos Próximos à Terra, classifica risco de colisão via ML e transmite alertas em tempo real.**

[![Java](https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![Python](https://img.shields.io/badge/Python-3.10+-3776AB?style=for-the-badge&logo=python&logoColor=white)](https://www.python.org/)
[![FastAPI](https://img.shields.io/badge/FastAPI-009688?style=for-the-badge&logo=fastapi&logoColor=white)](https://fastapi.tiangolo.com/)
[![Angular](https://img.shields.io/badge/Angular-21+-DD0031?style=for-the-badge&logo=angular&logoColor=white)](https://angular.dev/)
[![NASA API](https://img.shields.io/badge/NASA-NeoWS%20API-0B3D91?style=for-the-badge&logo=nasa)](https://api.nasa.gov/)
[![License](https://img.shields.io/badge/Licença-MIT-blue?style=for-the-badge)](LICENSE)

</div>

---

## 📡 Visão Geral

O **Space Radar** é um sistema de monitoramento em tempo real para **Objetos Próximos à Terra (NEOs — Near-Earth Objects)**. O projeto consome dados oficiais da NASA, processa as informações e utiliza um **modelo de Machine Learning** (integrado via Python/FastAPI) para classificar o risco de colisão de asteroides — transmitindo os resultados instantaneamente para um painel Angular via WebSockets.

---

## 🚀 Tecnologias Core

| Tecnologia | Finalidade |
|---|---|
| **Java 25** | Versão mais recente para máxima performance e sintaxe moderna |
| **Spring Boot 4.0.5** | Framework base para a construção da API e injeção de dependências |
| **WebSockets** | Comunicação bidirecional em tempo real com o front-end |
| **Python 3.10+** | Runtime do serviço de inferência ML |
| **FastAPI** | Framework Python de alta performance para o endpoint de inferência |
| **scikit-learn** | Treinamento e predição do modelo de machine learning |
| **NeoWS API** | Fonte oficial de dados da NASA para Objetos Próximos à Terra |
| **Angular 21+** | Dashboard front-end com radar tático e alertas de colisão |
| **SCSS** | Estilização de componentes e temas |

---

## 🛠️ Como o Radar Funciona?

O sistema opera em um fluxo cíclico de orquestração:

```
┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│  Coleta de Dados │────▶│  Orquestração    │───▶│  Inferência ML  │────▶│    Broadcast     │
│  (NASA NeoWS)    │     │  (a cada 10 seg) │     │  (FastAPI/POST)  │     │  (WebSocket)     │
└──────────────────┘     └──────────────────┘     └──────────────────┘     └──────────────────┘
                                                                                     │
                                                                                     ▼
                                                                          ┌──────────────────┐
                                                                          │ Dashboard Angular│
                                                                          │ (Radar + Alertas)│
                                                                          └──────────────────┘
```

1. **Data Harvesting** — O `NasaService` consome a API NeoWS da NASA. O código é agnóstico: pode buscar dados do dia atual para o radar em tempo real ou grandes volumes históricos para treinamento de modelos de IA.

2. **Orchestration** — A cada 10 segundos, o `RadarOrchestrator` dispara uma varredura automática (`@Scheduled`).

3. **ML Inference** — Os dados técnicos do asteroide (velocidade, diâmetro, magnitude, distância) são enviados via POST para o serviço de inferência Python.

4. **Broadcast** — Após a classificação do modelo, o resultado é transmitido instantaneamente via WebSocket para todos os clientes conectados.

5. **Dashboard** — O front-end Angular recebe os dados em tempo real e os renderiza em um radar tático interativo com alertas de colisão.

---

## 📋 Pré-requisitos

- ☕ **Java JDK 25** instalado
- 📦 **Maven 3.9+**
- 🐍 **Python 3.10+**
- 🔑 Uma **NASA API Key** (pode ser obtida em [api.nasa.gov](https://api.nasa.gov/))
- 🟢 **Node.js 20+** e **Angular CLI 21+**

---

## ⚙️ Configuração

Configure o arquivo `src/main/resources/META-INF/env.properties` com as seguintes chaves:

```properties
# Credenciais da NASA
nasa_api_key=SEU_TOKEN_AQUI

# Endpoint do modelo de Machine Learning (FastAPI)
ml_http_url=http://127.0.0.1:8000/predict

# Configurações do Servidor
server.port=8080
```

---

## 🏃 Como Rodar a API Java

**1. Clonar o repositório:**
```bash
git clone https://github.com/sam-umbra/space-radar.git
cd space-radar/back-end/space-radar-api
```

**2. Limpar e compilar o projeto:**
```bash
mvn clean install
```

**3. Executar a aplicação:**
```bash
mvn spring-boot:run
```

Com a aplicação rodando, as detecções podem ser acompanhadas via **logs do console** ou através de um **cliente WebSocket** apontado para `ws://localhost:8080`.

---

## 🐍 Como Rodar o Serviço Python ML

A API Java depende do servidor de inferência Python. Inicie-o **antes** da aplicação Java.

**1. Navegar para a pasta do serviço Python:**
```bash
cd space-radar/back-end/py-space-radar
```

**2. Instalar as dependências:**
```bash
pip install -r requirements.txt
# conda env update --file environment.yml --prune (se estiver usando conda)
```

**3. Executar o servidor FastAPI:**
```bash
uvicorn src.main:app --reload --host 127.0.0.1 --port 8000
```

O endpoint de inferência estará disponível em `http://127.0.0.1:8000/predict`, correspondendo à propriedade `ml_http_url` na configuração Java.

---

## 🌐 Como Rodar o Front-end Angular

**1. Navegar para a pasta do front-end:**
```bash
cd space-radar/front-end
```

**2. Instalar as dependências:**
```bash
npm install
```

**3. Iniciar o servidor de desenvolvimento:**
```bash
ng serve
```

O dashboard estará disponível em `http://localhost:4200`. Certifique-se de que a API Java e o serviço Python estão rodando antes de iniciar o front-end.

---

## 📁 Estrutura do Projeto

O projeto vive em um único repositório organizado da seguinte forma:

### ☕ Java API — `back-end/space-radar-api`
```
back-end/space-radar-api/
├── src/main/java/dev/umbra/space_radar_api/
│   ├── config/
│   │   ├── RestClientConfig.java         # Configuração do cliente HTTP
│   │   └── WebSocketConfig.java          # Configuração do WebSocket
│   ├── models/dtos/
│   │   ├── AsteroidData.java             # Modelo de dados NEO da NASA
│   │   └── PredictionResponse.java       # Modelo de resposta do ML
│   ├── schedulers/
│   │   └── RadarOrchestrator.java        # Varredura agendada (a cada 10s)
│   ├── services/
│   │   ├── ModelIntegrationService.java  # Integração com FastAPI
│   │   ├── NasaService.java              # Integração com NASA NeoWS
│   │   └── WebSocketService.java         # Broadcast em tempo real
│   └── SpaceRadarApiApplication.java     # Ponto de entrada da aplicação
├── resources/
│   └── META-INF/
│       ├── application.properties        # Configuração principal da aplicação
│       └── env.properties                # Variáveis de ambiente
└── pom.xml
```

### 🐍 Python ML — `back-end/py-space-radar`
```
back-end/py-space-radar/
├── data/
│   └── data.csv                          # Dataset de treinamento
├── models/
│   ├── radar_model.joblib                # Modelo ML treinado (scikit-learn)
│   └── scaler.joblib                     # Scaler de features
└── src/
    ├── config.py                         # Configuração do servidor
    ├── data_processor.py                 # Pipeline de pré-processamento
    ├── data_sim.py                       # Utilitários de simulação de dados
    ├── main.py                           # Ponto de entrada FastAPI
    └── train.py                          # Script de treinamento do modelo
```

### 🌐 Dashboard Angular — `front-end`
```
front-end/
├── public/
│   └── favicon.ico
├── src/
│   ├── app/
│   │   ├── components/
│   │   │   ├── approach-timeline/        # Gráfico de aproximação do asteroide
│   │   │   ├── asteroid-detail/          # Painel de detalhes do asteroide
│   │   │   ├── asteroid-table/           # Tabela de dados NEO
│   │   │   ├── layouts/                  # Componentes de layout
│   │   │   ├── risk-chart/               # Gráfico de risco de colisão
│   │   │   ├── sections/
│   │   │   │   ├── architecture-section/ # Seção de visão da arquitetura
│   │   │   │   ├── features-section/     # Seção de funcionalidades
│   │   │   │   ├── hero-section/         # Seção hero/landing
│   │   │   │   ├── tech-stack/           # Seção de stack tecnológica
│   │   │   │   └── threat-levels/        # Seção de níveis de ameaça
│   │   │   ├── star-field/               # Fundo animado de estrelas
│   │   │   └── stat-card/                # Card de estatísticas
│   │   ├── helpers/
│   │   │   ├── asteroid.mapper.ts        # Utilitários de mapeamento de dados
│   │   │   └── reveal.directive.ts       # Diretiva de scroll reveal
│   │   ├── models/
│   │   │   ├── asteroid.ts               # Interface de dados NEO
│   │   │   └── prediction-response.ts    # Interface de predição ML
│   │   ├── pages/
│   │   │   ├── dashboard/                # Dashboard do radar em tempo real
│   │   │   └── landing-page/             # Página de apresentação
│   │   ├── services/
│   │   │   └── websocket-service.ts      # Serviço cliente WebSocket
│   │   ├── app.config.ts
│   │   ├── app.html
│   │   ├── app.routes.ts
│   │   ├── app.scss
│   │   └── app.ts
│   ├── index.html
│   ├── main.ts
│   └── styles.scss
├── angular.json
├── package.json
├── tsconfig.json
├── tsconfig.app.json
└── tsconfig.spec.json
```

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Leia o [CONTRIBUTING.md](.github/CONTRIBUTING.md) antes de abrir um pull request.

---
