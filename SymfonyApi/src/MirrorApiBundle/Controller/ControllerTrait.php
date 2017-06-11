<?php
namespace MirrorApiBundle\Controller;


use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;

trait ControllerTrait
{
    public function userNotFound()
    {
        return new JsonResponse(['message' => 'User not found'], Response::HTTP_NOT_FOUND);
    }
}