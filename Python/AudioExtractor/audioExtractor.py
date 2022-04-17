import moviepy.editor as mp

my_clip = mp.VideoFileClip(r"video.mp4")

my_clip.audio.write_audiofile(r"audio.mp3")
