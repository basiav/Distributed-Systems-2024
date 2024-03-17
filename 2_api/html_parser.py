import numpy as np
import matplotlib.pyplot as plt
import base64
from io import BytesIO


def get_plt(xx, yy):
    # x = np.arange(0, 2 * np.pi, 0.1)
    # y = np.sin(x)

    fig, ax = plt.subplots()
    ax.scatter(xx, yy)

    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.set_title("Sinusoid")

    tmpfile = BytesIO()
    fig.savefig(tmpfile, format="jpeg")
    encoded = base64.b64encode(tmpfile.getvalue()).decode("utf-8")

    html = (
        "Some html head"
        + "<img src='data:image/jpeg;base64,{}' alt='Hello'>".format(encoded)
        + "Some more html"
    )
    ext = "{% extends 'base.html' %}"
    b = "{% block content %}"
    e = "{% endblock %}"

    html_content = """
    {}
    {}
    <html>
        <head>
            <title>Some HTML in here</title>
        </head>
        <body>
            <h1>{}</h1>
        </body>
    </html>
    {}
    """.format(
        ext, b, html, e
    )

    return html_content
