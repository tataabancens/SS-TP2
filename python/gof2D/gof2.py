import numpy as np
import matplotlib.pyplot as plt 
import matplotlib.animation as animation
import pandas as pd

df = pd.read_json('SS-TP2\python\maps.json')
grid = df.iloc[1]['map']


fig, ax = plt.subplots()
mat = ax.matshow(grid)
plt.show()