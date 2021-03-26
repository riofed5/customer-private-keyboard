# Private Keyboard Azure Function App

## Description
This is the Azure Function App responsible for handling the communication between the web application and the android application

## Prerequisites
* Microsoft Azure function app getting started guide is available at this [link](https://docs.microsoft.com/en-us/azure/azure-signalr/signalr-quickstart-azure-functions-javascript)

## Set up
* Instead of cloning the sample app repo in the guide, please use this repo instead
* Rename `local.settings.sample.json` to `local.settings.json`
* Update `AzureSignalRConnectionString` string to your Azure SignalR connection string
* Update `AzureWebJobsStorage` string to your Azure storage account connection string
* Start the function using `npm start`

## Q & A
* If there is CORS error shown in the website, please add your web url to the `CORS` string in `local.settings.json` separating by comma, e.g. `"http://localhost:3000,http://127.0.0.1:3000,..."`

## Contact authors
1. [Vy Nguyen](https://github.com/vynmetropolia)
2. [Nhan Mai](https://github.com/RenMai)
3. [Nhan Nguyen](https://github.com/riofed5)
4. [Quan Dao](https://github.com/dendimaniac)