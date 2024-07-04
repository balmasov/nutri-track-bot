# [nutri-track-bot](https://t.me/NutriTrackBot)

This project is designed to help count calories. It currently has two main functions:

## Track Your Food:
**Format:**
- name: "your name"
- raw weight: "weight in grams"
- cooked weight: "weight in grams"

## Calculate Raw Weight from Cooked Weight:
**Example:** Bob cooked rice. He started with 150 grams of raw rice, and after cooking, the weight changed to 370 grams. Bob saved this information in NutriTrackBot. Whenever Bob wants to eat, he can use the bot to determine how much cooked rice he needs to consume to match 45 grams of raw rice. (In this case, he needs to take 111 grams of cooked rice)

Now you might be wondering: "Why the hell would Bob count all this?" The short explanation is that calorie counting is most accurate with raw food weights. The long explanation about nutrients and other details is here: [article in english](https://dev.to/ivanbalmasov/why-losing-weight-is-only-possible-with-programming-4ifo), [article in russian](https://habr.com/ru/articles/826814/).

## How to Launch This Project

### There are two ways to launch this project:

**Simple Method: Launch with Docker**

There is no simple method, but you can find the task [github issue](https://github.com/balmasov/nutri-track-bot/issues) and create a Docker Compose description to make launching this project easier!

**Detailed Method**

**Register Your Bot:**
Register your bot via BotFather: [BotFather Tutorial](https://core.telegram.org/bots#6-botfather). The key step is obtaining your token and bot name. It's straightforward!

**Set Up Your Environment:**
After pulling the project from GitHub, configure your BOT_TOKEN and BOT_NAME local environments in the `template.yaml` file.

**Install Docker:**
Install Docker (required for a local AWS environment) [Docker Installation](https://docs.docker.com/get-docker/) and pull the PostgreSQL image [PostgreSQL Image](https://hub.docker.com/_/postgres). For convenience, you can also pull PGAdmin [PGAdmin](https://www.pgadmin.org/download/).

**Configure AWS Environment:**
Follow the guide to install AWS SAM CLI: [AWS SAM CLI Installation](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html).

**Run PostgreSQL Container:**
Start your PostgreSQL container and create a database with the schema `nutri_bot`. Create the required tables:
```sql
CREATE TABLE IF NOT EXISTS nutri_bot.users (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    step VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS nutri_bot.foods (
    id BIGSERIAL PRIMARY KEY,
    state VARCHAR(255),
    name VARCHAR(255),
    raw_weight BIGINT,
    cooked_weight BIGINT,
    user_id BIGINT REFERENCES nutri_bot.users (id)
);
```

**Set Up Environment Variables:**

Configure `CCH_PASS`, `CCH_URL`, and `CCH_USER` parameters in the `template.yaml` file. `CCH_URL` should have this form: `jdbc:postgresql://<you_ip>/<your_name>`. If using Docker Desktop, you can find your IP by clicking on your container, then click “Inspect,” then find the `IPAddress` parameter.

**Build and Run the Project:**
Ensure SAM and Docker are installed. Open a terminal in your project directory and run the following commands:
```sh
aws sso login
sam build
sam local start-api (use command "sam local start-api -d 5858" to run the app in debug mode)
```

**Set Up a Telegram Webhook:**

Note: The Telegram server expects a response “200 OK” after sending the update request. If it doesn't get it, the server will resend the update. This might cause repeated requests during the debugging process.

Install ngrok: [ngrok Download](https://ngrok.com/download) or pull the Docker image: [ngrok Docker Image](https://hub.docker.com/r/wernight/ngrok).
Use the command below to run your https tunnel. After running "sam local start-api," you'll see a link like this: `http://127.0.0.1:3000/cch`. Use the AWS app's port for the command:
```sh
ngrok http 3000
```

Then copy the provided link from the command (and your bot token from the BotFather) into the URL below to set up your local webhook
```sh
https://api.telegram.org/bot<YOUR_BOT_TOKEN>/setWebhook?url=<THE_LINK_FROM_NGROK/cch>
```

**Connect to the Debugging Process:**

Go to Recent Configurations -> Edit Configuration -> add a new configuration -> Remote JVM Debug -> in the "host" field, set this AWS Lambda app's IP example `127.0.0.1` and set the port to `5858`.
From your phone, send a message to this bot. Then click the debug button. You should then receive the message you sent. And your application will pause at your break point.

## How to Contribute to This Project
Find a task in the GitHub list of issues, code it, push it, and wait for approval. If you find something else you want to improve or fix, create an issue and code it.

## Architecture Explanation
As a project that runs on the AWS Lambda environment, the main task is to determine the user step and then invoke the right service for that step. A “step” is every available function in this bot, whether it's a command like `/start` or a keyboard button like `Cooking`. Each step involves three main stages in `LambdaHandler#handleRequest`:

**Request Parsing:**
InputStream is parsed into `Update` and then into `CustomUpdate`.

**User Identification:**
The most important part is to define the user's step, which is represented in the `UserStep` class.

**Service Invocation:**
Invoke the needed `StepService` implementation depending on the user's step. Each step is represented in a `StepService` implementation.
Each step can lead to a complete action and a message to the user, or it can lead to another step, creating a sequence. For example:

- **Complete Step:** Command `/start`. The user receives the start message based on their chatId and language settings from the `CustomUpdate#locale` field. No need to save the step or anything in the database; everything is retrieved from the `Update` (`CustomUpdate`) class.
- **Multi-step:** The `Cooking` button. If a user presses this button, the bot guides them through the cooking process. This involves saving the necessary information in the database and updating the user's step. The next interaction continues from the previous step: `STEEPLES` -> `SteplessServiceImpl`, `COOKING` -> `CookingServiceImpl`, and so on.

