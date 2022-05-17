# OT184-server
ONG-Java


# Authorize Login

This is a brief description of how to use the login users endpoint

## Postman
POST type request

Url: localhost8080/auth/login
## Body
Type of data to send: x-www-form-urlenconded
## API Reference

#### Post authorize login

```http
  POST /auth/login
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. Your username |
| `password` | `string` | **Required**. Your password |


#### singIn(username, password)

allows the user to login if he is previously registered, note that the password in the database must be encrypted

## Screenshots

![App Screenshot](https://github.com/alkemyTech/OT184-server/blob/OT184-29-welcome_email_template_integration/src/main/resources/static/successful%20authentication.png?raw=true)
