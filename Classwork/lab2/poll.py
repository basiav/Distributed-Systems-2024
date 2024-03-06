from fastapi import FastAPI
from enum import Enum
from pydantic import BaseModel
from typing import Union
from fastapi import Body, FastAPI, status
from fastapi.responses import JSONResponse
import random

app = FastAPI()

ID_COUNTER = 0

YES_STR = "yes"
NO_STR = "no"
MAYBE_STR = "maybe"


class VoteModel(str, Enum):
    YES = YES_STR
    NO = NO_STR
    MAYBE = MAYBE_STR


class Vote(BaseModel):
    id: str
    answer: VoteModel


class Poll(BaseModel):
    id: str
    votes: list[Vote]
    yes: int
    no: int
    maybe: int


def generate_sample_vote():
    global ID_COUNTER
    ID_COUNTER += 1
    random_answer = random.choice(list(VoteModel))
    return Vote(id=str(ID_COUNTER), answer=random_answer)


fake_votes_db = [generate_sample_vote() for _ in range(20)]
print(fake_votes_db)


def generate_poll(poll_id: str, votes: list[Vote]):
    counts = {YES_STR: 0, NO_STR: 0, MAYBE_STR: 0}
    for v in votes:
        counts[str(v.answer.value)] += 1
    return Poll(id=poll_id,
                votes=votes,
                yes=counts[YES_STR],
                no=counts[NO_STR],
                maybe=counts[MAYBE_STR]
                )


fake_polls_db = [
    generate_poll(str(i), votes=[fake_votes_db[i], fake_votes_db[i + 1]]) for i in range(10)
]
print(fake_polls_db)


# sample requests and queries
@app.get("/")
async def root():
    return {"message": "Hello in the poll api"}


# /poll
@app.get("/poll")
async def read_poll(skip: int = 0, limit: int = 10):
    return fake_polls_db[skip: skip + limit]


@app.post("/poll")
async def create_poll(poll_id: str):
    poll = Poll(poll_id, [])
    fake_polls_db.append(poll)
    return poll


# /poll/{poll_id}
@app.get("/poll/{poll_id}")
async def read_poll_id(
        poll_id: str
):
    return fake_polls_db[int(poll_id)]


@app.put("/poll/{poll_id}")
async def upsert_poll(
        poll_id: str,
        new_id: Union[str, None] = Body(default=None)
):
    for el in fake_polls_db:
        if el["id"] == poll_id:
            item = fake_polls_db[int(poll_id)]
            item["id"] = new_id
            return item
    else:
        item = generate_poll(poll_id, [])
        fake_polls_db.append(item)
        return JSONResponse(status_code=status.HTTP_201_CREATED, content=item)


@app.get("/poll/{poll_id}/vote")
async def read_poll_id(
        poll_id: str
):
    return fake_polls_db[int(poll_id)].votes


@app.get("/poll/{poll_id}/vote/{vote_id}")
async def read_user_item(
        poll_id: str,
        vote_id: str
):
    for v in fake_polls_db[int(poll_id)].votes:
        print(v)
        if v.id == vote_id:
            return v
    return JSONResponse(status_code=status.HTTP_404_NOT_FOUND,
                        content=f"Oops, no vote_id {vote_id} in poll_id {poll_id} "
                        )
