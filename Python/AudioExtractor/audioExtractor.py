import moviepy.editor as mp

my_clip = mp.VideoFileClip(r"videoplayback.mp4")

my_clip.audio.write_audiofile(r"ayat_kursi.mp3")
