import numpy as np
import matplotlib.pyplot as plt
import base64
from io import BytesIO


def get_encoded_img(xx, yy):
    fig, ax = plt.subplots()
    ax.scatter(xx, yy)

    ax.set_xlabel("x")
    ax.set_ylabel("y")
    ax.set_title("Sinusoid")

    tmpfile = BytesIO()
    fig.savefig(tmpfile, format="jpeg")
    encoded = base64.b64encode(tmpfile.getvalue()).decode("utf-8")

    return "data:image/jpeg;base64,{}".format(encoded)
