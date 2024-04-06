from cookiecutter.main import cookiecutter
from yaml import safe_load

context = safe_load(
"""
package_name: test
array:
  elements:
    - "1"
    - "b"
    - "1"
"""
)

# https://github.com/cookiecutter/cookiecutter/blob/main/cookiecutter/main.py
cookiecutter(
    template="Example-Project-1",
    output_dir="output",
    no_input=True,
    extra_context=context,
)
