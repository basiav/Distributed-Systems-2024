import matplotlib.pyplot as plt
import base64
from io import BytesIO


def get_encoded_img(xx, yy, title=None, locator=False):
    fig, ax = plt.subplots(tight_layout=True)
    yy = [round(float(y), 2) for y in yy]
    ax.scatter(xx, yy)

    if locator:
        ax.xaxis.set_major_locator(plt.MaxNLocator(len(xx) // 6))
        ax.yaxis.set_major_locator(plt.MaxNLocator(len(yy) // 10))

    ax.set_xlabel("Datetime")
    ax.set_ylabel("Temperature")
    ax.set_title(title)
    ax.tick_params(axis="x", labelrotation=90)

    tmpfile = BytesIO()
    fig.savefig(tmpfile, format="jpeg")
    encoded = base64.b64encode(tmpfile.getvalue()).decode("utf-8")

    return "data:image/jpeg;base64,{}".format(encoded)
