import numpy as np
import matplotlib.pyplot as plt 
import matplotlib.animation as animation
import pandas as pd

df = pd.read_json('results\maps50.json')
grid = df.iloc[0]['map']
i = 0
def update(data):
    global grid
    global i
    if(i < df.size):
        mat.set_data(df.iloc[i]['map'])
        i += 1
        return [mat]



fig, ax = plt.subplots()
mat = ax.matshow(grid)
ani = animation.FuncAnimation(fig, update, interval=100,
                              save_count=5000)
plt.show()