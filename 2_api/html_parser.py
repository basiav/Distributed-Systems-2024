import numpy as np
import matplotlib.pyplot as plt
import base64
from io import BytesIO


def get_encoded_img(xx, yy, title=None):
    fig, ax = plt.subplots(tight_layout=True)
    ax.scatter(xx, yy)

    ax.set_xlabel("Datetime")
    ax.set_ylabel("Temperature")
    ax.set_title(title)
    ax.tick_params(axis="x", labelrotation=90)

    tmpfile = BytesIO()
    fig.savefig(tmpfile, format="jpeg")
    encoded = base64.b64encode(tmpfile.getvalue()).decode("utf-8")

    return "data:image/jpeg;base64,{}".format(encoded)
