import numpy as np
import matplotlib.pyplot as plt 
import matplotlib.animation as animation
import pandas as pd
import matplotlib as mpl 

mpl.rcParams['animation.ffmpeg_path'] = r'C:\\Users\\tata_\SS\\ffmpeg-2022-08-31-git-319e8a49b5-essentials_build\bin\\ffmpeg.exe'

rule = '2'

df = pd.read_json('results\\rule' + rule + '\maps70.json')
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
anim = animation.FuncAnimation(fig, update, interval=100,
                              save_count=5000)
# plt.show()

f = r"C:\\Users\\tata_\SS\SS-TP2\\python\\gof2D\\animations\\gofRule2.mp4"
writervideo = animation.FFMpegWriter(fps=12) 
anim.save(f, writer=writervideo)