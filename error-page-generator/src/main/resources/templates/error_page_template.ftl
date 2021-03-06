<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="noindex, nofollow" />
    <title>${reasonPhrase}</title>
    <link rel="stylesheet" href="https://use.typekit.net/tzy2awt.css">
    <style>
        html,
        body {
            font-family: lato, sans-serif;
            font-weight: 300;
            font-style: normal;
            background-color: #fff;
            height: 100vh;
            margin: 0;
            color: #000;
        }

        .full-height {
            height: 100vh
        }

        .flex-center {
            align-items: center;
            display: flex;
            justify-content: center
        }

        .position-ref {
            position: relative
        }

        .value {
            border-right: 2px solid;
            font-size: 26px;
            padding: 0 10px 0 15px;
            text-align: center
        }

        .reason-phrase {
            font-size: 18px;
            text-align: center;
            padding: 10px
        }
    </style>
</head>

<body>
<div class="flex-center position-ref full-height">
    <div class="value">
        ${value}
    </div>
    <div class="reason-phrase">
        ${reasonPhrase}
    </div>
</div>
</body>
</html>