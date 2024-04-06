from jinja2 import Environment
from jinja2.ext import Extension
from cookiecutter.utils import simple_filter
from uuid_extensions import uuid7str


class FoobarExtension2(Extension):
    def __init__(self, environment):
        super(FoobarExtension2, self).__init__(environment)
        environment.filters["foobar2"] = lambda v: v * 2

class test(Extension):
    def __init__(self, environment):
        super(test, self).__init__(environment)
        environment.globals["test"] = lambda: "test!!!!"

@simple_filter
def simplefilterextension2(v):
    return v * 2


class UUIDExtension2(Extension):
    def __init__(self, environment: Environment) -> None:
        super().__init__(environment)

        def uuid7() -> str:
            return str(uuid7str())

        environment.globals.update(uuid7=uuid7)
        
        
class raise_exception_helper(Extension):
    def __init__(self, environment: Environment) -> None:
        super().__init__(environment)

        def raise_exception(msg):
            raise Exception(msg)

        environment.globals.update(raise_exception=raise_exception)
