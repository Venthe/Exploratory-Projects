from jinja2.ext import Extension
from cookiecutter.utils import simple_filter

class FoobarExtension(Extension):
    def __init__(self, environment):
        super(FoobarExtension, self).__init__(environment)
        environment.filters['foobar'] = lambda v: v * 2

@simple_filter
def simplefilterextension(v):
    return v * 2
