

import os
import subprocess

# {% raw %}
# '{% if cookiecutter.packaging != "pip" %}requirements.txt{% endif %}',
# '{% if cookiecutter.packaging != "poetry" %}poetry.lock{% endif %}',
# {% endraw %}
REMOVE_PATHS = [
    'this_file_shall_be_removed.txt'
]

for path in REMOVE_PATHS:
    path = path.strip()
    if path and os.path.exists(path):
        os.unlink(path) if os.path.isfile(path) else os.rmdir(path)

# subprocess.call(['git', 'init'])
# subprocess.call(['git', 'add', '*'])
# subprocess.call(['git', 'commit', '-m', 'Initial commit'])